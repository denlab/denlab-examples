require 'rubygems'
require 'yaml'

# A demonstration of YAML anchors, references and handling of nested values
# For more info, see: 
#   http://atechie.net/2009/07/merging-hashes-in-yaml-conf-files/

stooges = YAML::load( File.read('stooges.yml') )

print stooges
