#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'
PS4="+${0}> "
debug() {
    if ${debug:-false}; then
        echo "DEBUG> $@" 1>&2
    fi
}
# <workaroun that should be set on the machine> --------------------------------
if [[ -n "${TERM-}" ]]; then
    export TERM=xterm
fi
# </workaroun that should be set on the machine> -------------------------------
# if [[ "${name:-}" == "" ]] ; then export name="$(basename $0)"; fi

# debug "- exporting name..."
# if [[ "${name:-}" == "" ]]; then
    # debug "    - name not defined: defining ..."
export name="$(basename $(readlink -f $(which $0)))"
# else
    # debug "    - name is already defined (=${name}): not defining ..."
# fi
export dtBin="$(dirname $(readlink -f $(dirname $(readlink -f $0))/header.sh))"
# echo "\$dtBin=$dtBin"
if echo ${PATH} | tr : '\n' | grep -qx ${dtBin}; then
    export PATH=$PATH:${dtBin}
fi

