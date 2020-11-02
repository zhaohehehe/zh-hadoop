/*
* https://thrift.apache.org/
*/
namespace java thrift.tutorial

struct SharedStruct {
  1: i32 key
  2: string value
}

service TSharedService {
  SharedStruct getStruct(1: i32 key)
}