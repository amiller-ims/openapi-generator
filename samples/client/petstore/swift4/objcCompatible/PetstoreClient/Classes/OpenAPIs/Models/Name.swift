//
// Name.swift
//
// Generated by openapi-generator
// https://openapi-generator.tech
//

import Foundation

/** Model for testing model name same as property name */

@objc public class Name: NSObject, Codable {

    public var name: Int
    public var nameNum: NSNumber? {
        get {
            return name as NSNumber?
        }
    }
    public var snakeCase: Int?
    public var snakeCaseNum: NSNumber? {
        get {
            return snakeCase as NSNumber?
        }
    }
    public var property: String?
    public var _123number: Int?
    public var _123numberNum: NSNumber? {
        get {
            return _123number as NSNumber?
        }
    }

    public init(name: Int, snakeCase: Int?, property: String?, _123number: Int?) {
        self.name = name
        self.snakeCase = snakeCase
        self.property = property
        self._123number = _123number
    }

    public enum CodingKeys: String, CodingKey {
        case name
        case snakeCase = "snake_case"
        case property
        case _123number = "123Number"
    }

}
