package profile

import (
	"context"
	"grpc-golang/proto"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

type User struct {
	Age int64
}

var UserDetails = map[string]User{
	"John": {
		Age: 24,
	},
	"Mark": {
		Age: 25,
	},
}

type Server struct {
	proto.UnimplementedProfileServer
}

// Extending Grpc generated code to add custom implementation
func (s *Server) GetProfileByUserName(ctx context.Context, pr *proto.FetchProfileRequest) (*proto.FetchProfileResponse, error) {
	conn, err := grpc.Dial("localhost:9090", grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		return nil, err
	}

	// Creating Department gRPC client to fetch department details from Department service
	client := proto.NewDepartmentServiceClient(conn)
	dept, err := client.GetDepartmentByUserName(context.Background(), &proto.FetchDepartmentRequest{Name: pr.Name})
	if err != nil {
		return nil, err
	}

	return &proto.FetchProfileResponse{
		Name:       pr.Name,
		Age:        UserDetails[pr.Name].Age,
		Department: dept.Name,
	}, nil
}
