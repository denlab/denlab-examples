#!/usr/bin/env bash
set -euo pipefail
. common.sh

whitelistToNginxMap() {
    declare wl="$(cat)"
    if [[ "${wl}" == '' ]]; then
        echo
        return
    fi
    echo "${wl}"            \
        | sort              \
        | xargs -n1 host -r \
        | jq -R '
  capture("^(?<host>[^ ]+) has address (?<ip>.*)$")
| [
      "# \(.host) => \(.ip):"
    , "\(.ip)\t\(env.auth|@sh);"
  ]
'                               \
        | jq -s '.
| {body : .}
| .body |= ([["default $http_authorization;"]] + .)
| .body |= add
| .map = (
           (
              ["map $remote_addr $auth {"]
            + (.body | map("\t\(.)"))
            + ["}"]
           )
         )
'                               \
        | jq -r '.map | join("\n")'
}

nginxWhitelist() {
            whitelistToNginxMap   \
                | tee >(cat 1>&2) \
                | ssh nginx "cat > /etc/nginx/conf.d/whitelist.conf && ${nginxReload}"
}

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

declare nginxReload='/usr/sbin/nginx -s reload'
declare nginxActualConf='/usr/sbin/nginx -T'

# cleanup nginx conf
ssh nginx <<< "         \
   cd /etc/nginx/conf.d \
&& rm -f whitelist.conf \
&& ls -alrth            \
&& ${nginxReload}       \
"

export client2 auth
auth='Basic dXNlcjpwd2Q='
client2=$(host -r client2 | jq -Rr 'capture("^(?<host>[^ ]+) has address (?<ip>.*)$").ip')

nginxWhitelist <<< ''

expect ko 'noWhitelist_client1UnauthorizedAccess'            ssh client1 curl -sS --fail                 http://nginx
expect ko 'noWhitelist_client2UnauthorizedAccess'            ssh client2 curl -sS --fail                 http://nginx
expect ok 'noWhitelist_client1AuthentAccess'                 ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'noWhitelist_client2AuthentAccess'                 ssh client2 curl -sS --fail --user user:pwd http://nginx

nginxWhitelist <<< 'client2'

expect ko 'client2Whitelisted_client1UnauthorizedAccess'     ssh client1 curl -sS --fail                 http://nginx
expect ok 'client2Whitelisted_client2UnauthorizedAccess'     ssh client2 curl -sS --fail                 http://nginx
expect ok 'client2Whitelisted_client1AuthentAccess'          ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'client2Whitelisted_client2AuthentAccess'          ssh client2 curl -sS --fail --user user:pwd http://nginx

nginxWhitelist <<< 'client1'

expect ok 'client1Whitelisted_client1UnauthorizedAccess'     ssh client1 curl -sS --fail                 http://nginx
expect ko 'client1Whitelisted_client2UnauthorizedAccess'     ssh client2 curl -sS --fail                 http://nginx
expect ok 'client1Whitelisted_client1AuthentAccess'          ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'client1Whitelisted_client2AuthentAccess'          ssh client2 curl -sS --fail --user user:pwd http://nginx

nginxWhitelist <<< $'client1\nclient2'

expect ok 'client1And2Whitelisted_client1UnauthorizedAccess' ssh client1 curl -sS --fail                 http://nginx
expect ok 'client1And2Whitelisted_client2UnauthorizedAccess' ssh client2 curl -sS --fail                 http://nginx
expect ok 'client1And2Whitelisted_client1AuthentAccess'      ssh client1 curl -sS --fail --user user:pwd http://nginx
expect ok 'client1And2Whitelisted_client2AuthentAccess'      ssh client2 curl -sS --fail --user user:pwd http://nginx


