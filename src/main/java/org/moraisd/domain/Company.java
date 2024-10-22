package org.moraisd.domain;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import org.eclipse.microprofile.graphql.DefaultValue;

@Getter
@MongoEntity(collection = "companies")
public class Company extends PanacheMongoEntity {

  public String symbol;
  public String sector;
  public String industry;
  public String currency;
  @DefaultValue("0")
  public BigDecimal ebitda;
  @DefaultValue("0")
  public BigDecimal marketCapitalization;
  @DefaultValue("0")
  public BigDecimal peRatio;
  @DefaultValue("0")
  public BigDecimal evToEbitda;
  @DefaultValue("0")
  public BigDecimal operatingCashFlow;
  @DefaultValue("0")
  public BigDecimal capitalExpenditures;
  public String sharesOutstanding;

  public OffsetDateTime lastUpdated;

}
