package tech.xixing.qaqmall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/10 9:44
 */
@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;

    //@NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
}
