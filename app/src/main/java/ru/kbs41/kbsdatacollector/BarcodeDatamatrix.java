package ru.kbs41.kbsdatacollector;

public class BarcodeDatamatrix {

    private String idProductCode;
    private String productCode;
    private String idSerialNumber;
    private String serialNumber;
    private String otherData;

    private String finalData;

    public BarcodeDatamatrix() {
        this.idProductCode = "";
        this.productCode = "";
        this.idSerialNumber = "";
        this.serialNumber = "";
        this.otherData = "";
    }

    public String getIdProductCode() {
        return idProductCode;
    }

    public void setIdProductCode(String idProductCode) {
        this.idProductCode = idProductCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getIdSerialNumber() {
        return idSerialNumber;
    }

    public void setIdSerialNumber(String idSerialNumber) {
        this.idSerialNumber = idSerialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFinalData() {
        return finalData;
    }

    public void setFinalData(String finalData) {
        this.finalData = finalData;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public void parseDataMatrixBarcode(String barcode) {

        this.idProductCode = barcode.substring(0,2);
        this.productCode = barcode.substring(2,16);
        this.idSerialNumber = barcode.substring(16,18);
        this.serialNumber = barcode.substring(18,31);
        this.otherData = barcode.substring(31);

        this.finalData = idProductCode + productCode + idSerialNumber + serialNumber;

    }
}