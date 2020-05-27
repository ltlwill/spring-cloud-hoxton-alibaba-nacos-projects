/**
 * 
 */
package com.efe.ms.serviceconsumer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * <p>
 * 产品实体类:
 * </p>
 * 
 * @author Liu TianLong 2018年10月8日 上午9:41:52
 */

@SuppressWarnings("serial")
@Entity
@Table(name="products")
public class Product extends BusinessEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Transient
	private String sku;
	private String cname;
	private String ename;
	private String status;
	@Column(name="life_status")
	private Integer lifeStatus;
	@Column(name="hot_status")
	private Integer hotStatus;
	@Column(name="catid")
	private Integer catId;
	@Column(name="product_line")
	private Integer productLine;
	@Column(name="class")
	private Integer pclass;
	@Column(name="EbayCategory")
	private Integer ebayCategory;
	private String asp;
	private String asr;
	private Integer alert;
	private Integer quantity;
	@Column(name="pre_shelf_quantity")
	private Integer preShelfQuantity;
	private Integer outStock;
	private Integer fbaqty;
	@Transient
	private Date createTime;
	@Transient
	private Date archivetTime;
	private String us;
	private String au;
	private String de;
	private String es;
	private String it;
	private String ind;
	private String size;
	private String color;
	private String fabric;
	private String wareHouse;
	private String image;
	private String stockStatus;
	private Double weight;
	@Column(name="comment")
	private String ocomment;
	private Integer shipping;
	private Double price;
	@Column(name="unit_price")
	private Double unitPrice;
	@Column(name="develop_cost")
	private Double developCost;
	@Column(name="ext_price")
	private Double extPrice;
	@Column(name="last_purchase_price")
	private Double lastPurchasePrice;
	private Integer cost;
	private String isCombo;
	private String identity;
	private Integer mode;
	private Integer tag;
	private Integer hot;
	@Column(name="alert_day")
	private Integer alertDay;
	private String vendor;
	@Column(name="desc")
	private String odesc;
	@Column(name="ship_mode")
	private Integer shipMode;
	private String research;
	@Column(name="research_item_id")
	private String researchItemId;
	@Column(name="last_sale_qty")
	private Integer lastSaleQty;
	@Transient
	@Column(name="last_sale_date")
	private Date lastSaleDate;
	private Integer kpiQty;
	private Double kpiprice;
	@Transient
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	@Column(name="recommend_platform")
	private String recommendPlatform;
	@Column(name="overstock_at")
	private String overstockAt;
	@Column(name="cn_color")
	private String cnColor;
	private String toholding;
	@Column(name="pre_fbaqty")
	private Integer preFbaQty;
	@Column(name="tmp_comment")
	private String tmpComment;
	private String gsid;
	@Column(name="size_level")
	private Integer sizeLevel;
	@Column(name="exe_norm")
	private String exeNorm;
	@Column(name="safe_norm")
	private String safeNorm;
	@Column(name="ass_material")
	private String assMaterial;
	@Column(name="dingzhi_price")
	private Double dingzhiPrice;
	@Column(name="md_sale_price")
	private Double mdSalePrice;
	private String keyword;
	@Column(name="type_hot")
	private String typeHot;
	@Column(name="group_sku")
	private String groupSku;
	@Transient
	private String productLinesStr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		this.sku = id;
	}
	public String getSku() {
		return sku == null ? this.id : sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
		this.id = sku;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLifeStatus() {
		return lifeStatus;
	}
	public void setLifeStatus(Integer lifeStatus) {
		this.lifeStatus = lifeStatus;
	}
	public Integer getHotStatus() {
		return hotStatus;
	}
	public void setHotStatus(Integer hotStatus) {
		this.hotStatus = hotStatus;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public Integer getProductLine() {
		return productLine;
	}
	public void setProductLine(Integer productLine) {
		this.productLine = productLine;
	}
	public Integer getPclass() {
		return pclass;
	}
	public void setPclass(Integer pclass) {
		this.pclass = pclass;
	}
	public Integer getEbayCategory() {
		return ebayCategory;
	}
	public void setEbayCategory(Integer ebayCategory) {
		this.ebayCategory = ebayCategory;
	}
	public String getAsp() {
		return asp;
	}
	public void setAsp(String asp) {
		this.asp = asp;
	}
	public String getAsr() {
		return asr;
	}
	public void setAsr(String asr) {
		this.asr = asr;
	}
	public Integer getAlert() {
		return alert;
	}
	public void setAlert(Integer alert) {
		this.alert = alert;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getPreShelfQuantity() {
		return preShelfQuantity;
	}
	public void setPreShelfQuantity(Integer preShelfQuantity) {
		this.preShelfQuantity = preShelfQuantity;
	}
	public Integer getOutStock() {
		return outStock;
	}
	public void setOutStock(Integer outStock) {
		this.outStock = outStock;
	}
	public Integer getFbaqty() {
		return fbaqty;
	}
	public void setFbaqty(Integer fbaqty) {
		this.fbaqty = fbaqty;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getArchivetTime() {
		return archivetTime;
	}
	public void setArchivetTime(Date archivetTime) {
		this.archivetTime = archivetTime;
	}
	public String getUs() {
		return us;
	}
	public void setUs(String us) {
		this.us = us;
	}
	public String getAu() {
		return au;
	}
	public void setAu(String au) {
		this.au = au;
	}
	public String getDe() {
		return de;
	}
	public void setDe(String de) {
		this.de = de;
	}
	public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public String getIt() {
		return it;
	}
	public void setIt(String it) {
		this.it = it;
	}
	public String getInd() {
		return ind;
	}
	public void setInd(String ind) {
		this.ind = ind;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFabric() {
		return fabric;
	}
	public void setFabric(String fabric) {
		this.fabric = fabric;
	}
	public String getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getOcomment() {
		return ocomment;
	}
	public void setOcomment(String ocomment) {
		this.ocomment = ocomment;
	}
	public Integer getShipping() {
		return shipping;
	}
	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getDevelopCost() {
		return developCost;
	}
	public void setDevelopCost(Double developCost) {
		this.developCost = developCost;
	}
	public Double getExtPrice() {
		return extPrice;
	}
	public void setExtPrice(Double extPrice) {
		this.extPrice = extPrice;
	}
	public Double getLastPurchasePrice() {
		return lastPurchasePrice;
	}
	public void setLastPurchasePrice(Double lastPurchasePrice) {
		this.lastPurchasePrice = lastPurchasePrice;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getIsCombo() {
		return isCombo;
	}
	public void setIsCombo(String isCombo) {
		this.isCombo = isCombo;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public Integer getAlertDay() {
		return alertDay;
	}
	public void setAlertDay(Integer alertDay) {
		this.alertDay = alertDay;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getOdesc() {
		return odesc;
	}
	public void setOdesc(String odesc) {
		this.odesc = odesc;
	}
	public Integer getShipMode() {
		return shipMode;
	}
	public void setShipMode(Integer shipMode) {
		this.shipMode = shipMode;
	}
	public String getResearch() {
		return research;
	}
	public void setResearch(String research) {
		this.research = research;
	}
	public String getResearchItemId() {
		return researchItemId;
	}
	public void setResearchItemId(String researchItemId) {
		this.researchItemId = researchItemId;
	}
	public Integer getLastSaleQty() {
		return lastSaleQty;
	}
	public void setLastSaleQty(Integer lastSaleQty) {
		this.lastSaleQty = lastSaleQty;
	}
	public Date getLastSaleDate() {
		return lastSaleDate;
	}
	public void setLastSaleDate(Date lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}
	public Integer getKpiQty() {
		return kpiQty;
	}
	public void setKpiQty(Integer kpiQty) {
		this.kpiQty = kpiQty;
	}
	public Double getKpiprice() {
		return kpiprice;
	}
	public void setKpiprice(Double kpiprice) {
		this.kpiprice = kpiprice;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getRecommendPlatform() {
		return recommendPlatform;
	}
	public void setRecommendPlatform(String recommendPlatform) {
		this.recommendPlatform = recommendPlatform;
	}
	public String getOverstockAt() {
		return overstockAt;
	}
	public void setOverstockAt(String overstockAt) {
		this.overstockAt = overstockAt;
	}
	public String getCnColor() {
		return cnColor;
	}
	public void setCnColor(String cnColor) {
		this.cnColor = cnColor;
	}
	public String getToholding() {
		return toholding;
	}
	public void setToholding(String toholding) {
		this.toholding = toholding;
	}
	public Integer getPreFbaQty() {
		return preFbaQty;
	}
	public void setPreFbaQty(Integer preFbaQty) {
		this.preFbaQty = preFbaQty;
	}
	public String getTmpComment() {
		return tmpComment;
	}
	public void setTmpComment(String tmpComment) {
		this.tmpComment = tmpComment;
	}
	public String getGsid() {
		return gsid;
	}
	public void setGsid(String gsid) {
		this.gsid = gsid;
	}
	public Integer getSizeLevel() {
		return sizeLevel;
	}
	public void setSizeLevel(Integer sizeLevel) {
		this.sizeLevel = sizeLevel;
	}
	public String getExeNorm() {
		return exeNorm;
	}
	public void setExeNorm(String exeNorm) {
		this.exeNorm = exeNorm;
	}
	public String getSafeNorm() {
		return safeNorm;
	}
	public void setSafeNorm(String safeNorm) {
		this.safeNorm = safeNorm;
	}
	public String getAssMaterial() {
		return assMaterial;
	}
	public void setAssMaterial(String assMaterial) {
		this.assMaterial = assMaterial;
	}
	public Double getDingzhiPrice() {
		return dingzhiPrice;
	}
	public void setDingzhiPrice(Double dingzhiPrice) {
		this.dingzhiPrice = dingzhiPrice;
	}
	public Double getMdSalePrice() {
		return mdSalePrice;
	}
	public void setMdSalePrice(Double mdSalePrice) {
		this.mdSalePrice = mdSalePrice;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTypeHot() {
		return typeHot;
	}
	public void setTypeHot(String typeHot) {
		this.typeHot = typeHot;
	}
	public String getGroupSku() {
		return groupSku;
	}
	public void setGroupSku(String groupSku) {
		this.groupSku = groupSku;
	}
	public String getProductLinesStr() {
		return productLinesStr;
	}
	public void setProductLinesStr(String productLinesStr) {
		this.productLinesStr = productLinesStr;
	}
	
	
}
