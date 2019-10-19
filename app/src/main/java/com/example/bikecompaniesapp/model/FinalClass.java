package com.example.bikecompaniesapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalClass {

        private List<BikeCompany> networks;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public List<BikeCompany> getNetworks() {
            return networks;
        }

        public void setNetworks(List<BikeCompany> networks) {
            this.networks = networks;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
}
