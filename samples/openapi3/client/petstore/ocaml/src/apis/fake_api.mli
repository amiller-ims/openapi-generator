(*
 * This file has been generated by the OCamlClientCodegen generator for openapi-generator.
 *
 * Generated by: https://openapi-generator.tech
 *
 *)

val fake_health_get : unit -> Health_check_result.t Lwt.t
val fake_outer_boolean_serialize : body:bool -> unit -> bool Lwt.t
val fake_outer_composite_serialize : outer_composite_t:Outer_composite.t -> unit -> Outer_composite.t Lwt.t
val fake_outer_number_serialize : body:float -> unit -> float Lwt.t
val fake_outer_string_serialize : body:string -> unit -> string Lwt.t
val test_body_with_file_schema : file_schema_test_class_t:File_schema_test_class.t -> unit Lwt.t
val test_body_with_query_params : query:string -> user_t:User.t -> unit Lwt.t
val test_client_model : client_t:Client.t -> Client.t Lwt.t
val test_endpoint_parameters : number:float -> double:float -> pattern_without_delimiter:string -> byte:string -> ?integer:int32 -> ?int32:int32 -> ?int64:int64 -> ?float:float -> ?string:string -> ?binary:string -> ?date:string -> ?date_time:string -> ?password:string -> ?callback:string -> unit -> unit Lwt.t
val test_enum_parameters : ?enum_header_string_array:Enums.enum_form_string_array list -> ?enum_header_string:Enums.enumclass -> ?enum_query_string_array:Enums.enum_form_string_array list -> ?enum_query_string:Enums.enumclass -> ?enum_query_integer:Enums.enum_query_integer -> ?enum_query_double:Enums.enum_number -> ?enum_form_string_array:Enums.enum_form_string_array list -> ?enum_form_string:Enums.enumclass -> unit -> unit Lwt.t
val test_group_parameters : required_string_group:int32 -> required_boolean_group:bool -> required_int64_group:int64 -> ?string_group:int32 -> ?boolean_group:bool -> ?int64_group:int64 -> unit -> unit Lwt.t
val test_inline_additional_properties : request_body:(string * string) list -> unit Lwt.t
val test_json_form_data : param:string -> param2:string -> unit Lwt.t
