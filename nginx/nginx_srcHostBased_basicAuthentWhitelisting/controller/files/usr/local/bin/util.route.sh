#!/usr/bin/env bash

u.test.getNbAssert() {
    echo 0
}

u.route.test.fn.run() {
    set -euo pipefail
    declare desc='Run the given test function.'
    declare fn="${1}"
    shift 1
    declare nbAssertBefore="$(u.test.getNbAssert)"
    "${fn}"
    declare nbAssert=$(($(u.test.getNbAssert) - ${nbAssertBefore}))
    if [[ ${nbAssert} -eq 0 ]]; then
        u.info "Tests: run successfully."
    else
        u.info "Tests: ${nbAssert} assertion(s) ran successfully."
    fi

}

u.test.noTests() {
    u.info "No unit tests here (yet)."
}

u.route() {
    declare noArgsAllowed=false
    if [[ "${1:-}" == "--allow-no-args" ]]; then
        shift 1
        noArgsAllowed=true
    fi
    if [[ $# -eq 0 ]] && ! ${noArgsAllowed} ; then
        ${name}.usage 1>&2
        exit 1
    fi
    if [[ $# -eq 1 ]] ; then
        if [[ "${1}" == "-h"     ]] ||
               [[ "${1}" == "--help" ]]   ; then
            ${name}.usage 1>&2
            exit
        elif [[ "${1}" == "--test" ]]; then
            set -euo pipefail
            u.route.test.fn.run ${name}.test
            exit
        else
            :
        fi
    fi
}

util_route_module_loaded=true
