ssh() {
    SSH="$(which ssh)"
    # "${SSH}" -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no "$@"
    "${SSH}" "$@"
}

ssh_ping() {
    ssh "$1" echo "- ssh_ping: $1"
}

log() {
    echo "$@" 1>&2
}

expect() {
    declare ok
    if [[ "${1}" == 'ok' ]]; then
        ok=true
    elif [[ "${1}" == 'ko' ]]; then
        ok=false
    else
        log "ERROR:
- unknownAssertion: $1"
    fi
    shift 1
    declare testName="$1"
    shift 1
    line='                                                                                  '
    PROC_NAME="- ${testName}"
    if $ok; then
        PROC_NAME+=" shouldSucceed: "
    else
        PROC_NAME+=" shouldFail: "
    fi

    printf "%s %s " $PROC_NAME "${line:${#PROC_NAME}}" 1>&2
    declare rc success
    if "$@" &> /tmp/out; then
        success=true
    else
        success=false
    fi
    if ! $ok; then
        if ${success}; then
            success=false
        else
            success=true
        fi
    fi
    # set +xv
    if ${success}; then
        echo ok | GREP_COLOR='01;32' egrep --color=always '.*|$' 1>&2
    else
        echo KO | GREP_COLOR='01;41' egrep --color=always '.*|$' 1>&2
        log "\
  - expected : $($ok && echo returnCodeEquals0 || echo returnCodeNot0)
  - actual   : $($ok && echo not0              || echo 0             )
  - expr     : '$@'
  - stdouterr:
$(sed -r 's/.*/    - &/' /tmp/out)
"
        exit 1
    fi
}

