package org.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.project.exception.ValidationException;
import org.project.model.JobPosition;
import org.project.model.LastEducation;
import org.project.model.PaymentType;
import org.project.model.constant.Gender;
import org.project.model.constant.ItemCategory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MetaService {
    ObjectMapper on = new ObjectMapper();
    public Response jobPosition(){
        return Response.ok(JobPosition.findAll().list()).build();
    }
    public Response lastEducation(){
        return Response.ok(LastEducation.findAll().list()).build();
    }
    public Response paymentType(){
        return Response.ok(PaymentType.findAll().list()).build();
    }

    public Response gender(){
        List<Map<String, Object>> result = new ArrayList<>();
        for (Gender gender:Gender.values()){
            Map<String, Object> map = new HashMap<>();
            map.put("name", gender.getName());
            map.put("code", gender.getCode());
            result.add(map);
        }
        return Response.ok(result).build();
    }

    public Response itemCategory(){
        List<Map<String, Object>> result = new ArrayList<>();
        for (ItemCategory itemCategory:ItemCategory.values()){
            Map<String, Object> map = new HashMap<>();
            map.put("name", itemCategory.getName());
            result.add(map);
        }
        return Response.ok(result).build();
    }

    public Response bank() throws FileNotFoundException, JsonProcessingException {

        return Response.ok(getBankFromResources()).build();
    }
    public Response detailBank(String code) throws FileNotFoundException, JsonProcessingException {

        List<Map<String, Object>> bankList = getBankFromResources().
                stream().filter(s -> s.get("bank_code").toString().equals(code)).collect(Collectors.toList());
        if(bankList.isEmpty()){
            throw new ValidationException("INVALID_CODE");
        }
        return Response.ok(bankList.get(0)).build();
    }
    public Response province() throws UnirestException, JsonProcessingException {
        HttpResponse<String> httpResponse = Unirest.get("https://emsifa.github.io/api-wilayah-indonesia/api/provinces.json").asString();
        return Response.ok(on.readValue(httpResponse.getBody(), List.class)).build();
    }

    public Response city(String provinceId) throws UnirestException, JsonProcessingException {
        HttpResponse<String> httpResponse = Unirest.get(
                MessageFormat.format("https://emsifa.github.io/api-wilayah-indonesia/api/regencies/{0}.json", provinceId)
        ).asString();
        if (httpResponse.getStatus() != 200){
            throw new ValidationException("INVALID_PROVINCE_ID");
        }
        return Response.ok(on.readValue(httpResponse.getBody(), List.class)).build();
    }

    public Response district(String cityId) throws UnirestException, JsonProcessingException {
        HttpResponse<String> httpResponse = Unirest.get(
                MessageFormat.format("https://emsifa.github.io/api-wilayah-indonesia/api/districts/{0}.json", cityId)
        ).asString();
        if (httpResponse.getStatus() != 200){
            throw new ValidationException("INVALID_CITY_ID");
        }
        return Response.ok(on.readValue(httpResponse.getBody(), List.class)).build();
    }

    public Response village(String districtId) throws UnirestException, JsonProcessingException {
        HttpResponse<String> httpResponse = Unirest.get(
                MessageFormat.format("https://emsifa.github.io/api-wilayah-indonesia/api/villages/{0}.json", districtId)
        ).asString();
        if (httpResponse.getStatus() != 200){
            throw new ValidationException("INVALID_DISTRICT_ID");
        }
        return Response.ok(on.readValue(httpResponse.getBody(), List.class)).build();
    }

    public List<Map<String, Object>> getBankFromResources() throws FileNotFoundException, JsonProcessingException {
        File file = new File("./src/main/resources/bank.json");
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        scanner.close();
        return on.readValue(sb.toString(), List.class);
    }
}
