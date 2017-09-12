#!/usr/bin/env bash
# -*- mode: Shell-script -*-
set -euo pipefail

info() {
    echo "${indent:-}- $@" 1>&2
}
info.begin() {
    info "$@"
    if ! [[ -v indent ]]; then
        export indent=''
    fi
    indent+='  '
}
info.indent.dec() {
    hr
    jq -n '
            "  "
             env.indent
           | .[2:]
          '
}
info.off() {
    info "disabled: $@"
}
declare _fBegin='info.fBegin ${FUNCNAME}'
declare _fEnd='info.fEnd     ${FUNCNAME}'
info.fBegin() {
   info "[fn] ${1}" 
}
info.fEnd() {
    # info "[fn] ${1}" 
    :
}

info.test.dec() {
    eval ${_fBegin}
    (
        export indent='    '
        export actual="$(info.test.dec)"
        export expected="  "
        test.assertEq
    )
    eval ${_fEnd}
}
test.assertEq() {
    declare tmp=$(mktemp)
    declare rc
    set +e
    jq -en '
  {expected: env.expected, actual: env.actual}   | debug
| . + {expectedEqActual: (.expected == .actual)} | debug
| .expectedEqActual
'
    rc=$?
    echo "- rc=${rc}" 1>&2
    set -e
    if ! [[ "$rc" == "0" ]] ; then
        info "
Assertion failed!
- rc=$rc
"
    else
        info "one assertion passed!"
    fi
    rm $tmp
}
info.test() {
    eval ${_fBegin}
    info.off info.test.itest
    info.test.dec
    #     jq -en '
#   {expected: env.expected, actual: env.actual}   | debug
# | . + {expectedEqActual: (.expected == .actual)} | debug
# | .expectedEqActual
# '
    eval ${_fEnd}
}
info.end() {
    indent="$(jq -n '
            env.indent
          | .
          ')"
}
main() {
    eval ${_fBegin}
    info.test
    eval ${_fEnd}
}

main "$@"
