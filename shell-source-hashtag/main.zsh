
#!/usr/bin/env zsh
. $(dirname $(readlink -f $0))/header.zsh.sh

echo "BASH_VERSION=${BASH_VERSION:-<notFound>}"
