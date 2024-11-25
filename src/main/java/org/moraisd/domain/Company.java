package org.moraisd.domain;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Name;

@MongoEntity(collection = "companies")
@Getter
@Setter
public class Company extends PanacheMongoEntity {

  private String symbol;
  private String name;
  private String exchange;
  private String exchangeShortName;
  private String type;
  private String sector;
  private String industry;
  private String currency;
  private String isin;
  private String description;
  private String country;

  private BigDecimal price;
  private BigDecimal beta;
  private BigDecimal volAvg;
  private BigDecimal mktCap;
  private BigDecimal lastDiv;
  private String range;
  private BigDecimal changes;
  private String companyName;
  private String cik;
  private String cusip;
  private String website;
  private String ceo;
  private String fullTimeEmployees;
  private String phone;
  private String address;
  private String city;
  private String state;
  private String zip;
  private BigDecimal dcfDiff;
  private BigDecimal dcf;
  private String image;
  private String ipoDate;

  @DefaultValue("false")
  private boolean defaultImage;
  @DefaultValue("false")
  @Name("isEtf")
  private boolean etf;
  @DefaultValue("false")
  @Name("isActivelyTrading")
  private boolean activelyTrading;
  @Name("isFund")
  @DefaultValue("false")
  private boolean fund;
  @Name("isAdr")
  @DefaultValue("false")
  private boolean adr;

  private TtmFundamentalMetrics ttmFundamentalMetrics;
  private List<AnnualFundamentalMetrics> annualFundamentalMetrics;

  private LocalDateTime lastUpdated = LocalDateTime.now(ZoneOffset.UTC);

  public record TtmFundamentalMetrics(BigDecimal revenuePerShareTTM,
                                      BigDecimal netIncomePerShareTTM,
                                      BigDecimal operatingCashFlowPerShareTTM,
                                      BigDecimal freeCashFlowPerShareTTM,
                                      BigDecimal cashPerShareTTM,
                                      BigDecimal bookValuePerShareTTM,
                                      BigDecimal tangibleBookValuePerShareTTM,
                                      BigDecimal shareholdersEquityPerShareTTM,
                                      BigDecimal interestDebtPerShareTTM, BigDecimal marketCapTTM,
                                      BigDecimal enterpriseValueTTM, BigDecimal peRatioTTM,
                                      BigDecimal priceToSalesRatioTTM, BigDecimal pocfratioTTM,
                                      BigDecimal pfcfRatioTTM, BigDecimal pbRatioTTM,
                                      BigDecimal ptbRatioTTM, BigDecimal evToSalesTTM,
                                      BigDecimal enterpriseValueOverEBITDATTM,
                                      BigDecimal evToOperatingCashFlowTTM,
                                      BigDecimal evToFreeCashFlowTTM, BigDecimal earningsYieldTTM,
                                      BigDecimal freeCashFlowYieldTTM, BigDecimal debtToEquityTTM,
                                      BigDecimal debtToAssetsTTM, BigDecimal netDebtToEBITDATTM,
                                      BigDecimal currentRatioTTM, BigDecimal interestCoverageTTM,
                                      BigDecimal incomeQualityTTM, BigDecimal dividendYieldTTM,
                                      BigDecimal dividendYieldPercentageTTM,
                                      BigDecimal payoutRatioTTM,
                                      BigDecimal salesGeneralAndAdministrativeToRevenueTTM,
                                      BigDecimal researchAndDevelopmentToRevenueTTM,
                                      BigDecimal intangiblesToTotalAssetsTTM,
                                      BigDecimal capexToOperatingCashFlowTTM,
                                      BigDecimal capexToRevenueTTM,
                                      BigDecimal capexToDepreciationTTM,
                                      BigDecimal stockBasedCompensationToRevenueTTM,
                                      BigDecimal grahamNumberTTM, BigDecimal roicTTM,
                                      BigDecimal returnOnTangibleAssetsTTM,
                                      BigDecimal grahamNetNetTTM, BigDecimal workingCapitalTTM,
                                      BigDecimal tangibleAssetValueTTM,
                                      BigDecimal netCurrentAssetValueTTM,
                                      BigDecimal investedCapitalTTM,
                                      BigDecimal averageReceivablesTTM,
                                      BigDecimal averagePayablesTTM,
                                      BigDecimal averageInventoryTTM,
                                      BigDecimal daysSalesOutstandingTTM,
                                      BigDecimal daysPayablesOutstandingTTM,
                                      BigDecimal daysOfInventoryOnHandTTM,
                                      BigDecimal receivablesTurnoverTTM,
                                      BigDecimal payablesTurnoverTTM,
                                      BigDecimal inventoryTurnoverTTM,
                                      BigDecimal roeTTM, BigDecimal capexPerShareTTM,
                                      BigDecimal dividendPerShareTTM,
                                      BigDecimal debtToMarketCapTTM) {

    public TtmFundamentalMetrics {
    }
  }

  public record AnnualFundamentalMetrics(String symbol, String date,
                                         String calendarYear, String period,
                                         BigDecimal revenuePerShare, BigDecimal netIncomePerShare,
                                         BigDecimal operatingCashFlowPerShare,
                                         BigDecimal freeCashFlowPerShare, BigDecimal cashPerShare,
                                         BigDecimal bookValuePerShare,
                                         BigDecimal tangibleBookValuePerShare,
                                         BigDecimal shareholdersEquityPerShare,
                                         BigDecimal interestDebtPerShare, BigDecimal marketCap,
                                         BigDecimal enterpriseValue, BigDecimal peRatio,
                                         BigDecimal priceToSalesRatio, BigDecimal pocfratio,
                                         BigDecimal pfcfRatio, BigDecimal pbRatio,
                                         BigDecimal ptbRatio, BigDecimal evToSales,
                                         BigDecimal enterpriseValueOverEBITDA,
                                         BigDecimal evToOperatingCashFlow,
                                         BigDecimal evToFreeCashFlow, BigDecimal earningsYield,
                                         BigDecimal freeCashFlowYield, BigDecimal debtToEquity,
                                         BigDecimal debtToAssets, BigDecimal netDebtToEBITDA,
                                         BigDecimal currentRatio, BigDecimal interestCoverage,
                                         BigDecimal incomeQuality, BigDecimal dividendYield,
                                         BigDecimal payoutRatio,
                                         BigDecimal salesGeneralAndAdministrativeToRevenue,
                                         BigDecimal researchAndDevelopmentToRevenue,
                                         BigDecimal intangiblesToTotalAssets,
                                         BigDecimal capexToOperatingCashFlow,
                                         BigDecimal capexToRevenue, BigDecimal capexToDepreciation,
                                         BigDecimal stockBasedCompensationToRevenue,
                                         BigDecimal grahamNumber, BigDecimal roic,
                                         BigDecimal returnOnTangibleAssets,
                                         BigDecimal grahamNetNet,
                                         BigDecimal workingCapital, BigDecimal tangibleAssetValue,
                                         BigDecimal netCurrentAssetValue,
                                         BigDecimal investedCapital,
                                         BigDecimal averageReceivables, BigDecimal averagePayables,
                                         BigDecimal averageInventory,
                                         BigDecimal daysSalesOutstanding,
                                         BigDecimal daysPayablesOutstanding,
                                         BigDecimal daysOfInventoryOnHand,
                                         BigDecimal receivablesTurnover,
                                         BigDecimal payablesTurnover,
                                         BigDecimal inventoryTurnover, BigDecimal roe,
                                         BigDecimal capexPerShare) {

    public AnnualFundamentalMetrics {
    }
  }

}
