#!/usr/bin/env bash
# . header.sh
# . util.sh


f() {
    declare fn="${1}"
    shift 1
    u.info "- ${name}/${fn} $@"
    ${name}.${fn} "$@"
}

mod.sh.test() {
    u.info "no tests yet"
    false
}

mod.sh.usage() {
    cat <<EOF

Descr TODO
- Usage  : TODO
- Example: TODO

EOF
}

mod.sh.main() {
    u.route "$@"
}

if [[ "$0" == "$BASH_SOURCE" ]]; then
  mod.sh.main "$@"
fi
