redir = tee > $@.tmp  > /dev/null && mv $@.tmp $@

test  : ex.yml ac.yml ; diffcol $^ && touch $@
ac.yml: in.yml        ; yq -y . < $< | $(redir)
