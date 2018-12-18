# Configure the Docker provider
provider "docker" {
  # host = "tcp://${DOCKER_SERVER}:2376/"
  host = "unix:///var/run/docker.sock"
}

# Create a container
resource "docker_container" "foo" {
  image = "${docker_image.alpine.latest}"
  name  = "foo"
}

resource "docker_image" "alpine" {
  name = "alpine:latest"
}