<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citasAdiestramientoOwnersId">
    <h2>Citas Adiestramiento Del Dueño</h2>
     
     <c:if test="${noPuedePagar}">
    <h2>No puedes pagar la cita si no tienes suficiente dinero en el monedero</h2>
    </c:if>

    <table id="citasAdiestramientoOwnersIdTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre Animal</th>
        	<th>Tipo Animal</th>
            <th style="width: 150px;">Fecha de Inicio</th>
            <th>Duracion</th>
            <th>Precio</th>
            <th>Tipo Adiestramiento</th>
            <th>Adiestrador</th>
                        <th>Pagado</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citasAdiestramiento}" var="citaAdiestramiento">
            <tr>
                <td>
                    <c:out value="${citaAdiestramiento.pet.name}"/>
                </td>
                <td>
                    <c:out value="${citaAdiestramiento.pet.type}"/>
                </td>
                <td>
                    <c:out value="${citaAdiestramiento.fechaInicio}"/>
                </td>
                <td>
                    <c:out value="${citaAdiestramiento.duracion}"/>
                </td>
                <td>
                    <c:out value="${citaAdiestramiento.precio}"/>
                </td>
                <td>
                    <c:out value="${citaAdiestramiento.tipoAdiestramiento}"/>
                </td>
                <td>
                    <c:out value="${citaAdiestramiento.adiestrador.firstName}"/>
                </td>

   <td>
                    <c:out value="${citaAdiestramiento.pagado}"/>
                  </td>
                        


        <c:if test="${!citaAdiestramiento.pagado}">
	                <td>
	                	<spring:url value="/citaAdiestramiento/{citaAdiestramientoId}/pay" var="citaAdiestramientoUrl">
	                                    <spring:param name="citaAdiestramientoId" value="${citaAdiestramiento.id}"/>                                    
	                                </spring:url>
	                                <a href="${fn:escapeXml(citaAdiestramientoUrl)}">Pagar</a>
	                </td>
	            </c:if>





            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout> 