package main

import (
	"google.golang.org/grpc"
	"grpc-golang/profile"
	"grpc-golang/proto"
	"log"
	"net"
)

func main() {
	println("gRPC profile proto in Go")

	listener, err := net.Listen("tcp", ":9000")
	if err != nil {
		panic(err)
	}

	s := grpc.NewServer()

	// Binding Our Implementations in this case profile service
	proto.RegisterProfileServer(s, &profile.Server{})
	if err := s.Serve(listener); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
