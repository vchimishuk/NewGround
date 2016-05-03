package com.github.vchimishuk.newground.resource;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.vchimishuk.newground.resource.request.CalculateRequest;
import com.github.vchimishuk.newground.resource.result.ValueResult;
import com.github.vchimishuk.newground.service.NumbersService;

import org.springframework.stereotype.Controller;

@Controller
@Path("/numbers")
public class NumbersResource {
    private final NumbersService numbersService;

    @Inject
    public NumbersResource(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @GET
    @Path("/{index}")
    @Produces(MediaType.APPLICATION_XML)
    public Response get(@PathParam("index") int index) {
        ValueResult<BigDecimal> res = numbersService.get(index)
                .map(ValueResult<BigDecimal>::new)
                .orElseThrow(() -> new NotFoundException("Call post() first."));

        return Response.ok(res).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response post(CalculateRequest request) {
        ValueResult<Boolean> res = numbersService.calculate(request.getSource(), request.getDestination(), request.getNumber())
                .map(ValueResult<Boolean>::new)
                .orElseThrow(NotFoundException::new);

        return Response.ok(res).build();
    }
}
