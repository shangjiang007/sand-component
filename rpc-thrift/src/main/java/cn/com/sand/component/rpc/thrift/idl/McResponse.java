/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cn.com.sand.component.rpc.thrift.idl;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-02-18")
public class McResponse implements org.apache.thrift.TBase<McResponse, McResponse._Fields>, java.io.Serializable, Cloneable, Comparable<McResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("McResponse");

  private static final org.apache.thrift.protocol.TField ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("errorCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ERROR_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("errorMsg", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ERROR_SYS_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("errorSysCode", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("data", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new McResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new McResponseTupleSchemeFactory());
  }

  public String errorCode; // optional
  public String errorMsg; // optional
  public String errorSysCode; // optional
  public String data; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ERROR_CODE((short)1, "errorCode"),
    ERROR_MSG((short)2, "errorMsg"),
    ERROR_SYS_CODE((short)3, "errorSysCode"),
    DATA((short)4, "data");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ERROR_CODE
          return ERROR_CODE;
        case 2: // ERROR_MSG
          return ERROR_MSG;
        case 3: // ERROR_SYS_CODE
          return ERROR_SYS_CODE;
        case 4: // DATA
          return DATA;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.ERROR_CODE,_Fields.ERROR_MSG,_Fields.ERROR_SYS_CODE,_Fields.DATA};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("errorCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ERROR_MSG, new org.apache.thrift.meta_data.FieldMetaData("errorMsg", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ERROR_SYS_CODE, new org.apache.thrift.meta_data.FieldMetaData("errorSysCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATA, new org.apache.thrift.meta_data.FieldMetaData("data", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(McResponse.class, metaDataMap);
  }

  public McResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public McResponse(McResponse other) {
    if (other.isSetErrorCode()) {
      this.errorCode = other.errorCode;
    }
    if (other.isSetErrorMsg()) {
      this.errorMsg = other.errorMsg;
    }
    if (other.isSetErrorSysCode()) {
      this.errorSysCode = other.errorSysCode;
    }
    if (other.isSetData()) {
      this.data = other.data;
    }
  }

  public McResponse deepCopy() {
    return new McResponse(this);
  }

  @Override
  public void clear() {
    this.errorCode = null;
    this.errorMsg = null;
    this.errorSysCode = null;
    this.data = null;
  }

  public String getErrorCode() {
    return this.errorCode;
  }

  public McResponse setErrorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  public void unsetErrorCode() {
    this.errorCode = null;
  }

  /** Returns true if field errorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorCode() {
    return this.errorCode != null;
  }

  public void setErrorCodeIsSet(boolean value) {
    if (!value) {
      this.errorCode = null;
    }
  }

  public String getErrorMsg() {
    return this.errorMsg;
  }

  public McResponse setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
    return this;
  }

  public void unsetErrorMsg() {
    this.errorMsg = null;
  }

  /** Returns true if field errorMsg is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorMsg() {
    return this.errorMsg != null;
  }

  public void setErrorMsgIsSet(boolean value) {
    if (!value) {
      this.errorMsg = null;
    }
  }

  public String getErrorSysCode() {
    return this.errorSysCode;
  }

  public McResponse setErrorSysCode(String errorSysCode) {
    this.errorSysCode = errorSysCode;
    return this;
  }

  public void unsetErrorSysCode() {
    this.errorSysCode = null;
  }

  /** Returns true if field errorSysCode is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorSysCode() {
    return this.errorSysCode != null;
  }

  public void setErrorSysCodeIsSet(boolean value) {
    if (!value) {
      this.errorSysCode = null;
    }
  }

  public String getData() {
    return this.data;
  }

  public McResponse setData(String data) {
    this.data = data;
    return this;
  }

  public void unsetData() {
    this.data = null;
  }

  /** Returns true if field data is set (has been assigned a value) and false otherwise */
  public boolean isSetData() {
    return this.data != null;
  }

  public void setDataIsSet(boolean value) {
    if (!value) {
      this.data = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ERROR_CODE:
      if (value == null) {
        unsetErrorCode();
      } else {
        setErrorCode((String)value);
      }
      break;

    case ERROR_MSG:
      if (value == null) {
        unsetErrorMsg();
      } else {
        setErrorMsg((String)value);
      }
      break;

    case ERROR_SYS_CODE:
      if (value == null) {
        unsetErrorSysCode();
      } else {
        setErrorSysCode((String)value);
      }
      break;

    case DATA:
      if (value == null) {
        unsetData();
      } else {
        setData((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ERROR_CODE:
      return getErrorCode();

    case ERROR_MSG:
      return getErrorMsg();

    case ERROR_SYS_CODE:
      return getErrorSysCode();

    case DATA:
      return getData();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ERROR_CODE:
      return isSetErrorCode();
    case ERROR_MSG:
      return isSetErrorMsg();
    case ERROR_SYS_CODE:
      return isSetErrorSysCode();
    case DATA:
      return isSetData();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof McResponse)
      return this.equals((McResponse)that);
    return false;
  }

  public boolean equals(McResponse that) {
    if (that == null)
      return false;

    boolean this_present_errorCode = true && this.isSetErrorCode();
    boolean that_present_errorCode = true && that.isSetErrorCode();
    if (this_present_errorCode || that_present_errorCode) {
      if (!(this_present_errorCode && that_present_errorCode))
        return false;
      if (!this.errorCode.equals(that.errorCode))
        return false;
    }

    boolean this_present_errorMsg = true && this.isSetErrorMsg();
    boolean that_present_errorMsg = true && that.isSetErrorMsg();
    if (this_present_errorMsg || that_present_errorMsg) {
      if (!(this_present_errorMsg && that_present_errorMsg))
        return false;
      if (!this.errorMsg.equals(that.errorMsg))
        return false;
    }

    boolean this_present_errorSysCode = true && this.isSetErrorSysCode();
    boolean that_present_errorSysCode = true && that.isSetErrorSysCode();
    if (this_present_errorSysCode || that_present_errorSysCode) {
      if (!(this_present_errorSysCode && that_present_errorSysCode))
        return false;
      if (!this.errorSysCode.equals(that.errorSysCode))
        return false;
    }

    boolean this_present_data = true && this.isSetData();
    boolean that_present_data = true && that.isSetData();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_errorCode = true && (isSetErrorCode());
    list.add(present_errorCode);
    if (present_errorCode)
      list.add(errorCode);

    boolean present_errorMsg = true && (isSetErrorMsg());
    list.add(present_errorMsg);
    if (present_errorMsg)
      list.add(errorMsg);

    boolean present_errorSysCode = true && (isSetErrorSysCode());
    list.add(present_errorSysCode);
    if (present_errorSysCode)
      list.add(errorSysCode);

    boolean present_data = true && (isSetData());
    list.add(present_data);
    if (present_data)
      list.add(data);

    return list.hashCode();
  }

  @Override
  public int compareTo(McResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetErrorCode()).compareTo(other.isSetErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorCode, other.errorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetErrorMsg()).compareTo(other.isSetErrorMsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorMsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorMsg, other.errorMsg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetErrorSysCode()).compareTo(other.isSetErrorSysCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorSysCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorSysCode, other.errorSysCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetData()).compareTo(other.isSetData());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetData()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data, other.data);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("McResponse(");
    boolean first = true;

    if (isSetErrorCode()) {
      sb.append("errorCode:");
      if (this.errorCode == null) {
        sb.append("null");
      } else {
        sb.append(this.errorCode);
      }
      first = false;
    }
    if (isSetErrorMsg()) {
      if (!first) sb.append(", ");
      sb.append("errorMsg:");
      if (this.errorMsg == null) {
        sb.append("null");
      } else {
        sb.append(this.errorMsg);
      }
      first = false;
    }
    if (isSetErrorSysCode()) {
      if (!first) sb.append(", ");
      sb.append("errorSysCode:");
      if (this.errorSysCode == null) {
        sb.append("null");
      } else {
        sb.append(this.errorSysCode);
      }
      first = false;
    }
    if (isSetData()) {
      if (!first) sb.append(", ");
      sb.append("data:");
      if (this.data == null) {
        sb.append("null");
      } else {
        sb.append(this.data);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class McResponseStandardSchemeFactory implements SchemeFactory {
    public McResponseStandardScheme getScheme() {
      return new McResponseStandardScheme();
    }
  }

  private static class McResponseStandardScheme extends StandardScheme<McResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, McResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.errorCode = iprot.readString();
              struct.setErrorCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ERROR_MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.errorMsg = iprot.readString();
              struct.setErrorMsgIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ERROR_SYS_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.errorSysCode = iprot.readString();
              struct.setErrorSysCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.data = iprot.readString();
              struct.setDataIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, McResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.errorCode != null) {
        if (struct.isSetErrorCode()) {
          oprot.writeFieldBegin(ERROR_CODE_FIELD_DESC);
          oprot.writeString(struct.errorCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.errorMsg != null) {
        if (struct.isSetErrorMsg()) {
          oprot.writeFieldBegin(ERROR_MSG_FIELD_DESC);
          oprot.writeString(struct.errorMsg);
          oprot.writeFieldEnd();
        }
      }
      if (struct.errorSysCode != null) {
        if (struct.isSetErrorSysCode()) {
          oprot.writeFieldBegin(ERROR_SYS_CODE_FIELD_DESC);
          oprot.writeString(struct.errorSysCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.data != null) {
        if (struct.isSetData()) {
          oprot.writeFieldBegin(DATA_FIELD_DESC);
          oprot.writeString(struct.data);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class McResponseTupleSchemeFactory implements SchemeFactory {
    public McResponseTupleScheme getScheme() {
      return new McResponseTupleScheme();
    }
  }

  private static class McResponseTupleScheme extends TupleScheme<McResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, McResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetErrorCode()) {
        optionals.set(0);
      }
      if (struct.isSetErrorMsg()) {
        optionals.set(1);
      }
      if (struct.isSetErrorSysCode()) {
        optionals.set(2);
      }
      if (struct.isSetData()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetErrorCode()) {
        oprot.writeString(struct.errorCode);
      }
      if (struct.isSetErrorMsg()) {
        oprot.writeString(struct.errorMsg);
      }
      if (struct.isSetErrorSysCode()) {
        oprot.writeString(struct.errorSysCode);
      }
      if (struct.isSetData()) {
        oprot.writeString(struct.data);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, McResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.errorCode = iprot.readString();
        struct.setErrorCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.errorMsg = iprot.readString();
        struct.setErrorMsgIsSet(true);
      }
      if (incoming.get(2)) {
        struct.errorSysCode = iprot.readString();
        struct.setErrorSysCodeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.data = iprot.readString();
        struct.setDataIsSet(true);
      }
    }
  }

}

