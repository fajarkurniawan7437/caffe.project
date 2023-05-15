package org.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.project.model.JobPosition;
import org.project.model.LastEducation;
import org.project.model.PaymentType;
import org.project.model.constant.Gender;
import org.project.model.constant.ItemCategory;
import org.project.service.MetaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Path("/api/meta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MetaController {
    @Inject
    MetaService metaService;
    @GET
    @Path("/jobPosition")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = JobPosition.class)))
    })
    public Response jobPosition(){
        return metaService.jobPosition();
    }
    @GET
    @Path("/lastEducation")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LastEducation.class)))
    })
    public Response lastEducation(){
        return metaService.lastEducation();
    }
    @GET
    @Path("/paymentType")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PaymentType.class)))
    })
    public Response paymentType(){
        return metaService.paymentType();
    }

    @GET
    @Path("/gender")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Gender.class)))
    })
    public Response gender(){
        return metaService.gender();
    }

    @GET
    @Path("/itemCategory")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemCategory.class)))
    })
    public Response itemCategory(){
        return metaService.itemCategory();
    }

    @GET
    @Path("/bank")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)))
    })
    public Response bank() throws FileNotFoundException, JsonProcessingException {
        return metaService.bank();
    }

    @GET
    @Path("/bank/{code}")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Map.class)))
    })
    public Response detailBank(@PathParam("code") String code) throws FileNotFoundException, JsonProcessingException {
        return metaService.detailBank(code);
    }

    @GET
    @Path("/province")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)))
    })
    public Response province() throws JsonProcessingException, UnirestException {
        return metaService.province();
    }

    @GET
    @Path("/city/{provinceId}")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)))
    })
    public Response city(@PathParam("provinceId") String provinceId) throws JsonProcessingException, UnirestException {
        return metaService.city(provinceId);
    }

    @GET
    @Path("/district/{cityId}")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)))
    })
    public Response district(@PathParam("cityId") String cityId) throws JsonProcessingException, UnirestException {
        return metaService.district(cityId);
    }

    @GET
    @Path("/village/{districtId}")
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class)))
    })
    public Response village(@PathParam("districtId") String districtId) throws JsonProcessingException, UnirestException {
        return metaService.village(districtId);
    }
}
