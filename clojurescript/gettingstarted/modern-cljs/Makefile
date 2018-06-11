serve:
	boot serve -d target watch reload cljs-repl cljs target

repl_client:
	@echo
	@echo '# 1) start a repl client: '
	@echo '    (start-repl)'
	@echo '# 2) browse to:'
	@echo '    http://localhost:3000'
	@echo '# 3) check the cnx: '
	@echo '    (js/alert "hello from cljs")'
	@echo
	boot repl -c


compile:
	boot cljs target
