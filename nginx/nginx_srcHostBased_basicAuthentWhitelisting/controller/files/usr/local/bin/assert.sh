#!/usr/bin/env bash
set -euo pipefail
. f.log.sh


# <args> -----------------------------------------------------------------------
assert.args.count.eq() {
    declare expected=$1
    shift 1
    if [[ $# -ne $expected ]]; then
       u.info "
Assertion error!
- Expected     : $expected argument(s)
- But was      : $#
- For arguments: '$@'
"
       return 1
    fi
}
# </args> ----------------------------------------------------------------------
:
# <count> ----------------------------------------------------------------------
assert.lines.count.eq() {
    declare expectedCount="${1}"
    shift 1
    declare lines="${1}"
    shift 1
    declare actualCount=$(echo "${lines}" | egrep -v '^$' | wc -l)
    if [[ ${expectedCount} -ne ${actualCount} ]]; then
        u.info "
Assertion error!
- Expected line number: ${expectedCount}
- But was             : ${actualCount}
- For text            : '${lines}'
"
        return 1
    fi
}
# </count> ---------------------------------------------------------------------
:
# <common> ---------------------------------------------------------------------
assert.failed() {
    declare expectedMsg actualMsg
    expectedMsg="${1}"
    shift 1
    actualMsg="${1}"
    shift 1
    u.info
    u.info "Assertion failed!"
    u.info "- ${expectedMsg}"
    u.info "- ${actualMsg}"
    u.info
    return 1
}
# </common> --------------------------------------------------------------------
:
# <files> ----------------------------------------------------------------------
assert.path.neq.test() {
    t=$(mktemp -d )
    cd $t
    mkdir {a,b,c}
    tree
    assert.path.neq . .
    rm -rf $t
}
assert.currDir.haveFiles.test() {
    # f.begin "$FUNCNAME" "$@"
    declare t=$(mktemp -d )
    touch a b c
    assert.currDir.haveFiles a b c
    set +e
    assert.currDir.haveFiles a b c
    rc=$?
    set -e
    f.log "rc=$rc"
    ignore assert.expr.false 'assert.currDir.haveFiles a b c x &> /dev/null'
    rm -rf t
    # f.end
}
assert.currDir.haveFiles() {
    f.begin "$FUNCNAME" "$@"
        declare failed="
Assertion Failed:
- expected to have file '$1'
- in current directory: $(pwd)
- but was none
"
    declare result=0
    f.log "\$#=$#"
    case "$#" in
        0) assert.currDir.haveFiles.args.missing                   ; shift 1               ;;
        1) if ! [[ -f $1 ]]; then u.info "$failed" && f.end && return 1; fi ; shift 1 ;;
        *) for f in "$@"; do assert.currDir.haveFiles "${f}"; done ; shift 1               ;;
    esac
    f.end
}
assert.files.content.eq() {
    {
    echo -n "- $FUNCNAME" | t.recolor 69 '.*'
    echo -n ' '
    }         | tr -d '\n'
    echo    "$@" | t.recolor 211 '.*'

    declare path{1,2}Var{Name,Value}
    path1VarName="$1"
    expectedPath="$(eval echo $1)"
    shift 1
    u.showVar path1VarName expectedPath
    echo "\$@=$@"
    path2VarName="$1"
    actualPath="$(eval echo $1)"
    shift 1
    u.showVar path2VarName actualPath
    u.showVar expectedPath actualPath
    declare diffFile="$(mktemp)"
    diff ${expectedPath} ${actualPath} > $diffFile
    if ! [[ -s $diffFile ]]; then
        u.info "
ERROR!
- $FUNCNAME failure!
- Expected files ${expectedPath} and ${actualPath} equals
- but were not:
$(cat $diffFile)

"
        return 1
    fi
    rm $diffFile
}
assert.file.empty() {
    local f="${1}"
    shift 1
    if ! [[ "$(cat ${f})" == '' ]]; then
        assert.failed "expected: file '$f' empty" \
                      "but was not:
- file content: '$(cat $f)'
"
    fi
}
# </files> ---------------------------------------------------------------------
:
# <git> ------------------------------------------------------------------------
assert.git.is-clean() {
    if $(git.is-clean); then
        f.log gitRepoIsClean OK
    else
        u.info "
Assert error!
- supposed to have a clean git repo
- at current dir: $(pwd)
- but was not:
$(git status -sb)
"
        return 1
    fi
}
# </git> -----------------------------------------------------------------------
:
# <misc> -----------------------------------------------------------------------
assert.notSupposedToHappen() {
    u.info "
Assertion error:
- not supposed to happen
- likely a bug
"
    return 1
}
assert.currDir.haveFiles.args.missing() {
    f.begin "$FUNCNAME" "$@"
    :
    f.end
}
ignore() {
    if [[ "${1:-}" == "--logger" ]]; then
        shift 1
        declare logger="${1}"
        shift 1
        ${logger} "$@"
    else
        echo -n "[ignored]" | t.recolor 5 '.*'
        echo "[ignored] $@ "
        u.info "[ignored] $@ "
    fi
}
# </misc> ----------------------------------------------------------------------
:
# <paths> -----------------------------------------------------------------------
path.eq() {
    f.begin "$FUNCNAME" "$@"
    declare name1 name2 path1 path2 res
    name1="${1}"
    shift 1
    name2="${1}"
    shift 1
    path1="$(util.varname2val $name1)"
    path2="$(util.varname2val $name2)"
    if [[ "${path1}" == "${path2}" ]]; then
        res=0
    else
        res=1
    fi
    f.end
    return $res
}
assert.path.eq() {
    f.begin "$FUNCNAME" "$@"
    declare name1 name2 path1 path2
    name1="${1}"
    shift 1
    name2="${1}"
    shift 1
    path1="$(util.varname2val $name1)"
    path2="$(util.varname2val $name2)"
    if [[ "${path1}" != "${path2}" ]]; then
        u.info "
Assertion failed!
- Expected same paths for:
  - $name1: $path1
  - $name2: $path2
- But are not equal!
"
        f.end
        return 1
    else
        f.log assert.path.eq OK
    fi
    f.end
}
assert.path.neq() {
    f.begin "$FUNCNAME" "$@"
    declare name1 name2 path1 path2
    name1="${1}"
    shift 1
    name2="${1}"
    shift 1
    path1="$(util.varname2val $name1)"
    path2="$(util.varname2val $name2)"
    if [[ "${path1}" == "${path2}" ]]; then
        u.info "
Assertion failed!
- Expected different paths for:
  - $name1: $path1
  - $name2: $path2
- But are equal!
"
        f.end
        return 1
    else
        f.log assert.path.neq OK
    fi
    f.end
}
# </paths> ---------------------------------------------------------------------
:
# <test> -----------------------------------------------------------------------
assert.test() {
    f.begin "$FUNCNAME" "$@"
    ignore assert.currDir.haveFiles.test
    ignore assert.path.neq.test
    util.varname2val.test
}
# </test> ----------------------------------------------------------------------
:
# <values> ----------------------------------------------------------------------
assert.eq() {
    f.begin "$FUNCNAME" "$@"
    local expected="$1" actual="$2"
    var1Name="$1"
    expected="$(eval echo $1)"
    shift 1
    var2Name="$1"
    actual="$(eval echo $1)"
    shift 1
    if [[ "$expected" == "$actual" ]]; then
        f.log "${expected}==${actual}" OK
    else
        u.info
        u.info "- ERROR $FUNCNAME failure ! "
        u.info "- expected='$expected', but was: '${actual:-<EMPTY>}'"
        u.info
        exit 1
    fi
    f.end
}
assert.value.eq() {
    f.begin "$FUNCNAME" "$@"
    local expected="$1" actual="$2"
    if [[ "$expected" == "$actual" ]]; then
        f.log "${expected}==${actual}" OK
    else
        u.info
        u.info "- ERROR $FUNCNAME failure ! "
        u.info "- expected='$expected', but was: '${actual:-<EMPTY>}'"
        u.info
        exit 1
    fi
    f.end
}
assert.expr() {
    local rc
    set +e
    echo "$@" | bash -x
    rc=$?
    set -e
    if [[ $rc -ne 0 ]]; then
        u.info
        u.info "- ERROR assert failure ! "
        u.info "  - expected return code 0 for expr $@"
        u.info "  - but was : '$rc'"
        u.info
        exit 1
    fi
}
assert.expr.false() {
    f.begin "$FUNCNAME" "$@"
    if eval "$@" ; then
        u.info "
$FUNCNAME Failed!
  - For expr: $@
  - Expected: Evaluation return rc <> 0
  - But was : 0
"
        f.end.and-return 1
    fi
    f.end
}
assert.empty() {
    declare varName varValue
    varName="$1"
    varValue="$(eval echo $1)"
    if [[ -z "$varValue" ]]; then
        u.info "$FUNCNAME success !"
    else
        u.info
        u.info "- ERROR $FUNCNAME failure ! "
        u.info "- expecting empty value for '$varName', but was empty"
        u.info
        return 1
    fi
}
assert.notEmpty() {
    declare varName varValue
    varName="$1"
    varValue="$(eval echo $1)"
    if [[ -n "$varValue" ]]; then
        u.info "$FUNCNAME success !"
    else
        u.info
        u.info "- ERROR $FUNCNAME failure ! "
        u.info "- expecting non empty value for '$varName', but was empty"
        u.info
        return 1
    fi
}
# </values> ---------------------------------------------------------------------
:
# <vars> -----------------------------------------------------------------------
util.varname2val.test() {
    declare x=1
    actual="$(util.varname2val x)"
    echo "actual=${actual}"
    assert.eq 1 "${actual}"
}
util.varname2val() {
    declare name actual
    name="${1}"
    echo "$(eval echo \$${name})"
}
# </vars> -----------------------------------------------------------------------
:
assert.main() {
    f.begin "$FUNCNAME" "$@"
    assert.test
    f.end
}

if [[ "$0" == "$BASH_SOURCE" ]]; then
    set -euo pipefail
    PS4="+${0}> "
    assert.main "$@"
fi

assert_module_loaded=true
