<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citasOperacionesPets">
    <h2>Citas Operaciones Por Mascotas</h2>

    <table id="citasOperacionesPetsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Fecha de Inicio</th>
            <th>Hora</th>
            <th>Duracion</th>
            <th>Precio</th>
            <th>Veterinario</th>
            <th>Tipo Operacion</th>
            <th>Cantidad de Personal</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pet.citasOperacion}" var="citaOperacionPet">
            <tr>
                <td>
                    <c:out value="${citaOperacionPet.fechaInicio}"/>
                </td>
                <td>
                    <c:out value="${citaOperacionPet.hora}"/>
                </td>
                <td>
                    <c:out value="${citaOperacionPet.duracion}"/>
                </td>
                <td>
                    <c:out value="${citaOperacionPet.precio}"/>
                </td>
                <td>
                    <c:out value="${citaOperacionPet.vet.firstName}"/>
                </td>
                <td>
                    <c:out value="${citaOperacionPet.tipoOperacion}"/>
                </td>
                <td>
                    <c:out value="${citaOperacionPet.cantidadPersonal}"/>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout> 