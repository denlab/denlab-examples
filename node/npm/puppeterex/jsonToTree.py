from __future__ import print_function
import json
import sys

# Tree in JSON format
s = '{"Harry": {"children": ["Bill", {"Jane": {"children": [{"Diane": {"children": ["Mary"]}}, "Mark"]}}]}}'

print(s )
print()
# Convert JSON tree to a Python dict
data = json.loads(s)
print (data)
print()
# Convert back to JSON & print to stderr so we can verfiy that the tree is correct.
print(json.dumps(data, indent=4), file=sys.stderr)
print()

# Extract tree edges from the dict
edges = []

def get_edges(treedict, parent=None):
    name = next(iter(treedict.keys()))
    if parent is not None:
        edges.append((parent, name))
    for item in treedict[name]["children"]:
        if isinstance(item, dict):
            get_edges(item, parent=name)
        else:
            edges.append((name, item))

get_edges(data)

# Dump edge list in Graphviz DOT format
print('strict digraph tree {')
for row in edges:
    print('    {0} -> {1};'.format(*row))
print('}')
