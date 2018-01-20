include mkutil/begin
include mkutil/bash

img := test-img.jpg

ms_url := https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze?visualFeatures=Description,Tags&subscription-key=$(ms_ai_vision_key1)

$(shell mkdir build)
# <targets> --------------------------------------------------------------------------------

build/describe.img.url.json: build/img.url.json data/msUrl.json
	$(begin)
	cat $^ | jq -s add

data/msUrl.json:
	$(begin)
	jq -n '{msUrl: "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze?visualFeatures=Description,Tags&subscription-key=\(env.ms_ai_vision_key1)"}' | $(end)

# build/describe.img.url.json: build/img.url.json
#		$(begin)
#		curl  -H "Content-Type: application/json" -X POST -d@$< '$(ms_url)' \
#		| tee $@																														\
#		| jq .

build/img.url.json: data/img.jpg.url Makefile
	$(begin)
	jq -Rr '{url: .}' < $< | $(end)

clean:
	$(begin)
	rm -rf build
