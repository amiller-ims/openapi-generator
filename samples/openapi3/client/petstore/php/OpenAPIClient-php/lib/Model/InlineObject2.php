<?php
/**
 * InlineObject2
 *
 * PHP version 5
 *
 * @category Class
 * @package  OpenAPI\Client
 * @author   OpenAPI Generator team
 * @link     https://openapi-generator.tech
 */

/**
 * OpenAPI Petstore
 *
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 * Generated by: https://openapi-generator.tech
 * OpenAPI Generator version: 4.2.3-SNAPSHOT
 */

/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

namespace OpenAPI\Client\Model;

use \ArrayAccess;
use \OpenAPI\Client\ObjectSerializer;

/**
 * InlineObject2 Class Doc Comment
 *
 * @category Class
 * @package  OpenAPI\Client
 * @author   OpenAPI Generator team
 * @link     https://openapi-generator.tech
 */
class InlineObject2 implements ModelInterface, ArrayAccess
{
    const DISCRIMINATOR = null;

    /**
      * The original name of the model.
      *
      * @var string
      */
    protected static $openAPIModelName = 'inline_object_2';

    /**
      * Array of property to type mappings. Used for (de)serialization
      *
      * @var string[]
      */
    protected static $openAPITypes = [
        'enum_form_string_array' => 'string[]',
        'enum_form_string' => 'string'
    ];

    /**
      * Array of property to format mappings. Used for (de)serialization
      *
      * @var string[]
      */
    protected static $openAPIFormats = [
        'enum_form_string_array' => null,
        'enum_form_string' => null
    ];

    /**
     * Array of property to type mappings. Used for (de)serialization
     *
     * @return array
     */
    public static function openAPITypes()
    {
        return self::$openAPITypes;
    }

    /**
     * Array of property to format mappings. Used for (de)serialization
     *
     * @return array
     */
    public static function openAPIFormats()
    {
        return self::$openAPIFormats;
    }

    /**
     * Array of attributes where the key is the local name,
     * and the value is the original name
     *
     * @var string[]
     */
    protected static $attributeMap = [
        'enum_form_string_array' => 'enum_form_string_array',
        'enum_form_string' => 'enum_form_string'
    ];

    /**
     * Array of attributes to setter functions (for deserialization of responses)
     *
     * @var string[]
     */
    protected static $setters = [
        'enum_form_string_array' => 'setEnumFormStringArray',
        'enum_form_string' => 'setEnumFormString'
    ];

    /**
     * Array of attributes to getter functions (for serialization of requests)
     *
     * @var string[]
     */
    protected static $getters = [
        'enum_form_string_array' => 'getEnumFormStringArray',
        'enum_form_string' => 'getEnumFormString'
    ];

    /**
     * Array of attributes where the key is the local name,
     * and the value is the original name
     *
     * @return array
     */
    public static function attributeMap()
    {
        return self::$attributeMap;
    }

    /**
     * Array of attributes to setter functions (for deserialization of responses)
     *
     * @return array
     */
    public static function setters()
    {
        return self::$setters;
    }

    /**
     * Array of attributes to getter functions (for serialization of requests)
     *
     * @return array
     */
    public static function getters()
    {
        return self::$getters;
    }

    /**
     * The original name of the model.
     *
     * @return string
     */
    public function getModelName()
    {
        return self::$openAPIModelName;
    }

    const ENUM_FORM_STRING_ARRAY_GREATER_THAN = '>';
    const ENUM_FORM_STRING_ARRAY_DOLLAR = '$';
    const ENUM_FORM_STRING_ABC = '_abc';
    const ENUM_FORM_STRING_EFG = '-efg';
    const ENUM_FORM_STRING_XYZ = '(xyz)';
    

    
    /**
     * Gets allowable values of the enum
     *
     * @return string[]
     */
    public function getEnumFormStringArrayAllowableValues()
    {
        return [
            self::ENUM_FORM_STRING_ARRAY_GREATER_THAN,
            self::ENUM_FORM_STRING_ARRAY_DOLLAR,
        ];
    }
    
    /**
     * Gets allowable values of the enum
     *
     * @return string[]
     */
    public function getEnumFormStringAllowableValues()
    {
        return [
            self::ENUM_FORM_STRING_ABC,
            self::ENUM_FORM_STRING_EFG,
            self::ENUM_FORM_STRING_XYZ,
        ];
    }
    

    /**
     * Associative array for storing property values
     *
     * @var mixed[]
     */
    protected $container = [];

    /**
     * Constructor
     *
     * @param mixed[] $data Associated array of property values
     *                      initializing the model
     */
    public function __construct(array $data = null)
    {
        $this->container['enum_form_string_array'] = isset($data['enum_form_string_array']) ? $data['enum_form_string_array'] : null;
        $this->container['enum_form_string'] = isset($data['enum_form_string']) ? $data['enum_form_string'] : '-efg';
    }

    /**
     * Show all the invalid properties with reasons.
     *
     * @return array invalid properties with reasons
     */
    public function listInvalidProperties()
    {
        $invalidProperties = [];

        $allowedValues = $this->getEnumFormStringAllowableValues();
        if (!is_null($this->container['enum_form_string']) && !in_array($this->container['enum_form_string'], $allowedValues, true)) {
            $invalidProperties[] = sprintf(
                "invalid value for 'enum_form_string', must be one of '%s'",
                implode("', '", $allowedValues)
            );
        }

        return $invalidProperties;
    }

    /**
     * Validate all the properties in the model
     * return true if all passed
     *
     * @return bool True if all properties are valid
     */
    public function valid()
    {
        return count($this->listInvalidProperties()) === 0;
    }


    /**
     * Gets enum_form_string_array
     *
     * @return string[]|null
     */
    public function getEnumFormStringArray()
    {
        return $this->container['enum_form_string_array'];
    }

    /**
     * Sets enum_form_string_array
     *
     * @param string[]|null $enum_form_string_array Form parameter enum test (string array)
     *
     * @return $this
     */
    public function setEnumFormStringArray($enum_form_string_array)
    {
        $allowedValues = $this->getEnumFormStringArrayAllowableValues();
        if (!is_null($enum_form_string_array) && array_diff($enum_form_string_array, $allowedValues)) {
            throw new \InvalidArgumentException(
                sprintf(
                    "Invalid value for 'enum_form_string_array', must be one of '%s'",
                    implode("', '", $allowedValues)
                )
            );
        }
        $this->container['enum_form_string_array'] = $enum_form_string_array;

        return $this;
    }

    /**
     * Gets enum_form_string
     *
     * @return string|null
     */
    public function getEnumFormString()
    {
        return $this->container['enum_form_string'];
    }

    /**
     * Sets enum_form_string
     *
     * @param string|null $enum_form_string Form parameter enum test (string)
     *
     * @return $this
     */
    public function setEnumFormString($enum_form_string)
    {
        $allowedValues = $this->getEnumFormStringAllowableValues();
        if (!is_null($enum_form_string) && !in_array($enum_form_string, $allowedValues, true)) {
            throw new \InvalidArgumentException(
                sprintf(
                    "Invalid value for 'enum_form_string', must be one of '%s'",
                    implode("', '", $allowedValues)
                )
            );
        }
        $this->container['enum_form_string'] = $enum_form_string;

        return $this;
    }
    /**
     * Returns true if offset exists. False otherwise.
     *
     * @param integer $offset Offset
     *
     * @return boolean
     */
    public function offsetExists($offset)
    {
        return isset($this->container[$offset]);
    }

    /**
     * Gets offset.
     *
     * @param integer $offset Offset
     *
     * @return mixed
     */
    public function offsetGet($offset)
    {
        return isset($this->container[$offset]) ? $this->container[$offset] : null;
    }

    /**
     * Sets value based on offset.
     *
     * @param integer $offset Offset
     * @param mixed   $value  Value to be set
     *
     * @return void
     */
    public function offsetSet($offset, $value)
    {
        if (is_null($offset)) {
            $this->container[] = $value;
        } else {
            $this->container[$offset] = $value;
        }
    }

    /**
     * Unsets offset.
     *
     * @param integer $offset Offset
     *
     * @return void
     */
    public function offsetUnset($offset)
    {
        unset($this->container[$offset]);
    }

    /**
     * Gets the string presentation of the object
     *
     * @return string
     */
    public function __toString()
    {
        return json_encode(
            ObjectSerializer::sanitizeForSerialization($this),
            JSON_PRETTY_PRINT
        );
    }

    /**
     * Gets a header-safe presentation of the object
     *
     * @return string
     */
    public function toHeaderValue()
    {
        return json_encode(ObjectSerializer::sanitizeForSerialization($this));
    }
}


