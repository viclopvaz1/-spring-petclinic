<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="citasAdiestramiento">

	<h2>Cita Adiestramiento Information</h2>


	<table class="table table-striped">
		<tr>

			<th>Nombre Animal</th>
			<td><b><c:out value="${citaAdiestramiento.pet.name}" /></b></td>
		</tr>
		<tr>
			<th>Tipo Animal</th>
			<td><c:out value="${citaAdiestramiento.pet.type}" /></td>
		</tr>
		<tr>
			<th>Fecha de Inicio</th>
			<td><c:out value="${citaAdiestramiento.fechaInicio}" /></td>
		</tr>
		<tr>
			<th>Duracion</th>
			<td><c:out value="${citaAdiestramiento.duracion}" /></td>
		</tr>
		<tr>
			<th>Precio</th>
			<td><c:out value="${citaAdiestramiento.precio}" /></td>
		</tr>
		<tr>
			<th>Adiestrador</th>
			<td><c:out value="${citaAdiestramiento.adiestrador.firstName}" /></td>
		</tr>
		<tr>
			<th>Tipo Operacion</th>
			<td><c:out value="${citaAdiestramiento.tipoAdiestramiento}" /></td>
		</tr>
		<tr>
			<th>Propietario</th>
			<td><c:out value="${citaAdiestramiento.owner.firstName}" /></td>
		</tr>
    <tr>
            <th>Pagado</th>
            <td><c:out value="${citaAdiestramiento.pagado}"/></td>
        </tr>
	</table>

	<spring:url
		value="{citaAdiestramientoId}/edit/{ownerId}/{petId}"
		var="editUrl">
		<spring:param name="citaAdiestramientoId" value="${citaAdiestramiento.id}" />
		<spring:param name="ownerId" value="${citaAdiestramiento.owner.id}" />
		<spring:param name="petId" value="${citaAdiestramiento.pet.id}" />


	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit </a>

	<spring:url value="{citaAdiestramientoId}/delete" var="deleteUrl">
		<spring:param name="citaAdiestramientoId"
			value="${citaAdiestramiento.id}" />
	</spring:url>
	<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete
	</a>

	<br />
	<br />
	<br />


</petclinic:layout>

