package be.tobania.demo.kafka.paymentService.controller;

import be.tobania.demo.kafka.paymentService.model.Order;
import be.tobania.demo.kafka.paymentService.model.Payment;
import be.tobania.demo.kafka.paymentService.model.PaymentLine;
import be.tobania.demo.kafka.paymentService.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;


    @ApiOperation(value = "Add a new payment", nickname = "addPayment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Order.class),
            @ApiResponse(code = 405, message = "Invalid input")})
    @RequestMapping(produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public Payment addPayment(@Valid @RequestBody @NotNull Payment payment) {
        log.info("add new payment");

        return paymentService.createPayment(payment);
    }

    @ApiOperation(value = "add a new payment line for an existing payment and update payment status when it is totally paid", nickname = "addPaymentLine")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Payment not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    @PostMapping(
            value = "/{paymentId}/paymentLine",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public Payment addPaymentLine(@ApiParam(value = "ID of payment to add a payment line", required = true) @PathVariable("paymentId") Long paymentId
            , @ApiParam(value = "PaymentLine object that needs to be created", required = true) @Valid @RequestBody PaymentLine paymentLine) {
        log.info("create");
        return paymentService.createPaymentLine(paymentId, paymentLine);
    }


    @ApiOperation(value = "Finds Payments by status", nickname = "findPaymentsByStatus", notes = "Multiple status values can be provided with comma separated strings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(
            value = "/findByStatus",
            produces = {"application/json"}
    )
    public List<Payment> findPaymentsByStatus(@NotNull @ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "placed, payed, canceled") @Valid @RequestParam(value = "status", required = true) List<String> status) {

        return paymentService.getPaymentsByStatus(status);
    }


    @ApiOperation(value = "Find payment by ID", nickname = "getPaymentById", notes = "Returns a single payment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Payment not found")})
    @GetMapping(
            value = "/{paymentId}",
            produces = {"application/json"}
    )
    public Payment getPaymentById(@ApiParam(value = "ID of payment to return", required = true) @PathVariable("paymentId") Long paymentId) {
        return null;
    }


}
