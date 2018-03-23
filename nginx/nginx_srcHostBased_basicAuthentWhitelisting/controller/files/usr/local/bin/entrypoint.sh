#!/usr/bin/env bash
set -euo pipefail
. common.sh

# whitelistToNginxMap() {
#     declare wl="$(cat)"
#     if [[ "${wl}" == '' ]]; then
#         echo
#         return
#     fi
#     echo "${wl}"            \
#         | sort              \
#         | xargs -n1 host -r \
#         | jq -R '
#   capture("^(?<host>[^ ]+) has address (?<ip>.*)$")
# | [
#       "# \(.host) => \(.ip):"
#     , "\(.ip)\t\(env.auth|@sh);"
#   ]
# '                               \
#         | jq -s '.
# | {body : .}
# | .body |= ([["default $http_authorization;"]] + .)
# | .body |= add
# | .map = (
#            (
#               [
#                   "server { location / { proxy_set_header Authorization   $auth;   }}"
#                 , "map $remote_addr $auth {"
#               ]
#             + (.body | map("\t\(.)"))
#             + ["}"]
#            )
#          )
# '                               \
#         | jq -r '.map | join("\n")'
# }

# nginxWhitelist() {
#             whitelistToNginxMap   \
#                 | tee >(cat 1>&2) \
#                 | ssh nginx "cat > /etc/nginx/conf.d/whitelist.conf && ${nginxReload}"
# }

for h in client2:22              \
             client2:22          \
             nginx:80            \
             protected_server:80 \
             unprotected_server:8080; do
    wait-for-it.sh $h
done

expect ok sshPingClient1 ssh_ping client1
expect ok sshPingClient2 ssh_ping client2
expect ok sshPingNginx   ssh_ping nginx

log '- check init setup'
ssh nginx <<< "         \
   set -xv              \
&& cd /etc/nginx/conf.d/ && ls | sort \
"


declare nginxReload='/usr/sbin/nginx -s reload'
declare nginxActualConf='/usr/sbin/nginx -T'

# cleanup nginx conf
log '- cleanup'

ssh nginx <<< "         \
   set -xv              \
&& cd /etc/nginx/conf.d/ && ls | sort && ls | sort | xargs -i bash -c 'echo \"- {}:\" && cat {}' \
&& cat /etc/nginx/conf.d/default.conf \
&& cd /etc/nginx/conf.d \
&& rm -f aaa.whitelist.conf \
&& ls -alrth            \
&& ${nginxReload}       \
"

export client2 auth
auth='Basic dXNlcjpwd2Q='
client2=$(host -r client2 | jq -Rr 'capture("^(?<host>[^ ]+) has address (?<ip>.*)$").ip')

access() {
    declare client auth
    client "$1"
    shift 1
    if [[ $# -gt 0 ]]; then
        auth="$1"
        shift 1
    fi
    if [[ -z "${auth}" ]]; then
        ssh "${client}" -sS --fail --user "${auth}" http://nginx | grep 'hello world'
    else
        ssh "${client}" -sS --fail                  http://nginx | grep 'hello world'
    fi
}

nginx_conf() {
    log '- nginx_conf:'
    ssh nginx "bash -xveuo pipefail -c 'nginx -T'"
}

log '- clear whitelist'
nginx_whitelist <<< ''
# nginx_conf

expect ko 'noWhitelist_client1UnauthorizedAccess'            ssh client1 curl -sS --fail                 http://nginx
expect ko 'noWhitelist_client2UnauthorizedAccess'            ssh client2 curl -sS --fail                 http://nginx
expect ok 'noWhitelist_client1AuthentAccess'                 ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'noWhitelist_client2AuthentAccess'                 ssh client2 curl -sS --fail --user user:pwd http://nginx

nginx_whitelist <<< 'client2'
# nginx_conf

expect ko 'client2Whitelisted_client1UnauthorizedAccess'     ssh client1 curl -sS --fail                 http://nginx
expect ok 'client2Whitelisted_client2UnauthorizedAccess'     ssh client2 curl -sS --fail                 http://nginx
expect ok 'client2Whitelisted_client1AuthentAccess'          ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'client2Whitelisted_client2AuthentAccess'          ssh client2 curl -sS --fail --user user:pwd http://nginx

nginx_whitelist <<< 'client1'
# nginx_conf

expect ok 'client1Whitelisted_client1UnauthorizedAccess'     ssh client1 curl -sS --fail                 http://nginx
expect ko 'client1Whitelisted_client2UnauthorizedAccess'     ssh client2 curl -sS --fail                 http://nginx
expect ok 'client1Whitelisted_client1AuthentAccess'          ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'client1Whitelisted_client2AuthentAccess'          ssh client2 curl -sS --fail --user user:pwd http://nginx

nginx_whitelist <<< $'client1\nclient2'
# nginx_conf

expect ok 'client1And2Whitelisted_client1UnauthorizedAccess' ssh client1 curl -sS --fail                 http://nginx
expect ok 'client1And2Whitelisted_client2UnauthorizedAccess' ssh client2 curl -sS --fail                 http://nginx
expect ok 'client1And2Whitelisted_client1AuthentAccess'      ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'client1And2Whitelisted_client2AuthentAccess'      ssh client2 curl -sS --fail --user user:pwd http://nginx

