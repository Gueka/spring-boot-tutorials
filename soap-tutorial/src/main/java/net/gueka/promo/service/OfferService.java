package net.gueka.promo.service;

import java.util.List;

import net.gueka.promo.schema.Data;

public interface OfferService {
    List<String> getOffers(Data data);
}