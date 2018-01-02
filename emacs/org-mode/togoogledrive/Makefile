include mkutil/begin
include mkutil/bash

cryptocurr_sync_down: cryptocurr_fileid
	./gdrive sync download --keep-remote $(shell cat $<) cryptocurr

list:
	./gdrive list

cryptocurr_sync_up: cryptocurr_fileid cryptocurr/doc.html
	./gdrive sync upload cryptocurr $(shell cat $<)

cryptocurr_fileid: cryptocurr_fileid_line
	[[ "$$(wc -l < $< )" == 1 ]]
	cut -d' ' -f1 < $< | $(end)

cryptocurr_fileid_line:
	./gdrive list | egrep 'cryptocurr *dir ' | $(end)

cryptocurr/doc.html: doc.org
	emacs doc.org --batch -f org-html-export-to-html
	mv doc.html cryptocurr

gdrive_list: gdrive
	./gdrive list

gdrive:
	curl -L 'https://docs.google.com/uc?id=0B3X9GlR6EmbnQ0FtZmJJUXEyRTA&export=download' > $@
	chmod +x $@	
