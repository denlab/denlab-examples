#!/usr/bin/env groovy


class C {
  def i = 42
  def m() {
    def i=1024
    println "xhello from ShadowClassInstance.groovy ! i=$i"
  }
}

def c = new C()

c.m()
