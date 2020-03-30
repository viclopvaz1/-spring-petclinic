<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citasOperaciones">

    <h2>Cita Operacion Information</h2>


    <table class="table table-striped">
        <tr>

            <th>Nombre Animal</th>
            <td><b><c:out value="${citaOperacion.pet.name}"/></b></td>
        </tr>
        <tr>
            <th>Tipo Animal</th>
            <td><c:out value="${citaOperacion.pet.type}"/></td>
        </tr>
        <tr>
            <th>Fecha de Inicio</th>
            <td><c:out value="${citaOperacion.fechaInicio}"/></td>
        </tr>
        <tr>
            <th>Hora</th>
            <td><c:out value="${citaOperacion.hora}"/></td>
        </tr>
        <tr>
            <th>Duracion</th>
            <td><c:out value="${citaOperacion.duracion}"/></td>
        </tr>
        <tr>
            <th>Precio</th>
            <td><c:out value="${citaOperacion.precio}"/></td>
        </tr>
         <tr>
            <th>Veterinario</th>
            <td><c:out value="${citaOperacion.vet.firstName}"/></td>
        </tr>
        <tr>
            <th>Tipo Operacion</th>
            <td><c:out value="${citaOperacion.tipoOperacion}"/></td>
        </tr>
        <tr>
            <th>Cantidad de Personal</th>
            <td><c:out value="${citaOperacion.cantidadPersonal}"/></td>
        </tr>
    </table>

    <spring:url value="{citaOperacionId}/edit/{petId}" var="editUrl">
        <spring:param name="citaOperacionId" value="${citaOperacion.id}"/>
        <spring:param name="petId" value="${citaOperacion.pet.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit </a>

 	<spring:url value="{citaOperacionId}/delete" var="deleteUrl">
        <spring:param name="citaOperacionId" value="${citaOperacion.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete </a>

    <br/>
    <br/>
    <br/>
   

</petclinic:layout>

