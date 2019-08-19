package net.gueka.promo.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.log4j.Log4j2;
import net.gueka.promo.schema.ObjectFactory;
import net.gueka.promo.schema.PromotionRequest;
import net.gueka.promo.schema.PromotionResponse;
import net.gueka.promo.service.OfferService;

@Log4j2
@Endpoint
public class ServiceEndpoint {
	
	public static final String NAMESPACE_URI = "http://www.gueka.net/promo/schema";
	private static final String SUBMIT_SM_REQ = "promotionRequest";
	
	@Autowired
	OfferService service;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = SUBMIT_SM_REQ)
	@ResponsePayload
	public PromotionResponse getOffers(@RequestPayload PromotionRequest request) {
		log.info("A message just arrive with userId: " + request.getData().getUserId());
		return generateResponse(service.getOffers(request.getData()));
	}

	private PromotionResponse generateResponse(List<String> offers) {
		PromotionResponse response = new ObjectFactory().createPromotionResponse();
		response.getOffers().addAll(offers);
		return response;
	}
}