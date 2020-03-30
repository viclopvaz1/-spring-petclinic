<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causas">

    <h2>Causa Information</h2>


    <table class="table table-striped">
        <tr>

            <th>Fecha de inicio</th>
            <td><b><c:out value="${causa.fechaInicio}"/></b></td>
        </tr>
        <tr>
            <th>Fecha final</th>
            <td><c:out value="${causa.fechaFin}"/></td>
        </tr>
        <tr>
            <th>ONG</th>
            <td><c:out value="${causa.ong}"/></td>
        </tr>
        <tr>
            <th>Cantidad a alcanzar</th>
            <td><c:out value="${causa.objetivo}"/></td>
        </tr>
        <tr>
            <th>Cantidad recaudada</th>
            <td><c:out value="${causa.dineroRecaudado}"/></td>
        </tr>
         <tr>
            <th>Validez</th>
            <td><c:out value="${causa.valido}"/></td>
        </tr>
    </table>

	<c:choose>
              <c:when test="${!user}">
    <spring:url value="{causaId}/edit" var="editUrl">
        <spring:param name="causaId" value="${causa.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit </a>

 	<spring:url value="{causaId}/delete" var="deleteUrl">
        <spring:param name="causaId" value="${causa.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete </a>
  </c:when>
         <c:otherwise>
         </c:otherwise>
        </c:choose>
		<c:choose>
              <c:when test="${causa.valido}">
              <spring:url value="/donacion/{causaId}/new" var="causaUrl">
        	<spring:param name="causaId" value="${causa.id}"/>
   		</spring:url>
   		<a href="${fn:escapeXml(causaUrl)}" class="btn btn-default">Hacer Donacion</a>
              </c:when>
         <c:otherwise>
         </c:otherwise>
        </c:choose>
        

    <br/>
    <br/>
    <br/>
   

</petclinic:layout>

