<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="crearCausa">
    <h2>
        <c:if test="${causa['new']}">New </c:if> Causa
    </h2>
    <b>${mensaje}</b>	
	
   	
    <form:form modelAttribute="causa" class="form-horizontal" id="add-causa-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Fecha de inicio" name="fechaInicio"/>
            <petclinic:inputField label="Fecha de fin" name="fechaFin"/>
            <petclinic:inputField label="ONG" name="ong"/>
            <petclinic:inputField label="Objetivo" name="objetivo"/>
            <petclinic:inputField label="Dinero Recaudado" name="dineroRecaudado"/>
            <c:choose>
				<c:when test="${!causa['new']}">
                   <petclinic:inputField label="Validez" name="valido"/>
                </c:when>
             <c:otherwise>
             </c:otherwise>
             </c:choose>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${causa['new']}">
                        <button class="btn btn-default" type="submit">Add Causa</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Causa</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
