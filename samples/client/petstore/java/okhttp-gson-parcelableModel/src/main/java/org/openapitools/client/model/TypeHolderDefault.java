/*
 * OpenAPI Petstore
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcel;

/**
 * TypeHolderDefault
 */

public class TypeHolderDefault implements Parcelable {
  public static final String SERIALIZED_NAME_STRING_ITEM = "string_item";
  @SerializedName(SERIALIZED_NAME_STRING_ITEM)
  private String stringItem = "what";

  public static final String SERIALIZED_NAME_NUMBER_ITEM = "number_item";
  @SerializedName(SERIALIZED_NAME_NUMBER_ITEM)
  private BigDecimal numberItem;

  public static final String SERIALIZED_NAME_INTEGER_ITEM = "integer_item";
  @SerializedName(SERIALIZED_NAME_INTEGER_ITEM)
  private Integer integerItem;

  public static final String SERIALIZED_NAME_BOOL_ITEM = "bool_item";
  @SerializedName(SERIALIZED_NAME_BOOL_ITEM)
  private Boolean boolItem = true;

  public static final String SERIALIZED_NAME_ARRAY_ITEM = "array_item";
  @SerializedName(SERIALIZED_NAME_ARRAY_ITEM)
  private List<Integer> arrayItem = new ArrayList<Integer>();

  public TypeHolderDefault() {
  }

  public TypeHolderDefault stringItem(String stringItem) {
    
    this.stringItem = stringItem;
    return this;
  }

   /**
   * Get stringItem
   * @return stringItem
  **/
  @ApiModelProperty(required = true, value = "")

  public String getStringItem() {
    return stringItem;
  }


  public void setStringItem(String stringItem) {
    this.stringItem = stringItem;
  }


  public TypeHolderDefault numberItem(BigDecimal numberItem) {
    
    this.numberItem = numberItem;
    return this;
  }

   /**
   * Get numberItem
   * @return numberItem
  **/
  @ApiModelProperty(required = true, value = "")

  public BigDecimal getNumberItem() {
    return numberItem;
  }


  public void setNumberItem(BigDecimal numberItem) {
    this.numberItem = numberItem;
  }


  public TypeHolderDefault integerItem(Integer integerItem) {
    
    this.integerItem = integerItem;
    return this;
  }

   /**
   * Get integerItem
   * @return integerItem
  **/
  @ApiModelProperty(required = true, value = "")

  public Integer getIntegerItem() {
    return integerItem;
  }


  public void setIntegerItem(Integer integerItem) {
    this.integerItem = integerItem;
  }


  public TypeHolderDefault boolItem(Boolean boolItem) {
    
    this.boolItem = boolItem;
    return this;
  }

   /**
   * Get boolItem
   * @return boolItem
  **/
  @ApiModelProperty(required = true, value = "")

  public Boolean getBoolItem() {
    return boolItem;
  }


  public void setBoolItem(Boolean boolItem) {
    this.boolItem = boolItem;
  }


  public TypeHolderDefault arrayItem(List<Integer> arrayItem) {
    
    this.arrayItem = arrayItem;
    return this;
  }

  public TypeHolderDefault addArrayItemItem(Integer arrayItemItem) {
    this.arrayItem.add(arrayItemItem);
    return this;
  }

   /**
   * Get arrayItem
   * @return arrayItem
  **/
  @ApiModelProperty(required = true, value = "")

  public List<Integer> getArrayItem() {
    return arrayItem;
  }


  public void setArrayItem(List<Integer> arrayItem) {
    this.arrayItem = arrayItem;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypeHolderDefault typeHolderDefault = (TypeHolderDefault) o;
    return Objects.equals(this.stringItem, typeHolderDefault.stringItem) &&
        Objects.equals(this.numberItem, typeHolderDefault.numberItem) &&
        Objects.equals(this.integerItem, typeHolderDefault.integerItem) &&
        Objects.equals(this.boolItem, typeHolderDefault.boolItem) &&
        Objects.equals(this.arrayItem, typeHolderDefault.arrayItem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stringItem, numberItem, integerItem, boolItem, arrayItem);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypeHolderDefault {\n");
    sb.append("    stringItem: ").append(toIndentedString(stringItem)).append("\n");
    sb.append("    numberItem: ").append(toIndentedString(numberItem)).append("\n");
    sb.append("    integerItem: ").append(toIndentedString(integerItem)).append("\n");
    sb.append("    boolItem: ").append(toIndentedString(boolItem)).append("\n");
    sb.append("    arrayItem: ").append(toIndentedString(arrayItem)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public void writeToParcel(Parcel out, int flags) {
    out.writeValue(stringItem);
    out.writeValue(numberItem);
    out.writeValue(integerItem);
    out.writeValue(boolItem);
    out.writeValue(arrayItem);
  }

  TypeHolderDefault(Parcel in) {
    stringItem = (String)in.readValue(null);
    numberItem = (BigDecimal)in.readValue(BigDecimal.class.getClassLoader());
    integerItem = (Integer)in.readValue(null);
    boolItem = (Boolean)in.readValue(null);
    arrayItem = (List<Integer>)in.readValue(null);
  }

  public int describeContents() {
    return 0;
  }

  public static final Parcelable.Creator<TypeHolderDefault> CREATOR = new Parcelable.Creator<TypeHolderDefault>() {
    public TypeHolderDefault createFromParcel(Parcel in) {
      return new TypeHolderDefault(in);
    }
    public TypeHolderDefault[] newArray(int size) {
      return new TypeHolderDefault[size];
    }
  };
}

