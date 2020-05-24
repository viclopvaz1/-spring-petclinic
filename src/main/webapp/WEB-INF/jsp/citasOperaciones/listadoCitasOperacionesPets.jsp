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
    
    <c:if test="${noPuedePagar}">
    <h2>No puedes pagar la cita si no tienes suficiente dinero en el monedero</h2>
    </c:if>

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
            <th>Pagado</th>
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
                <td>
                    <c:out value="${citaOperacionPet.pagado}"/>
                </td>
                <c:if test="${!citaOperacionPet.pagado}">
	                <td>
	                	<spring:url value="/citaOperacion/{citaOperacionId}/pay" var="citaOperacionUrl">
	                                    <spring:param name="citaOperacionId" value="${citaOperacionPet.id}"/>                                    
	                                </spring:url>
	                                <a href="${fn:escapeXml(citaOperacionUrl)}">Pagar</a>
	                </td>
	            </c:if>


            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout> 