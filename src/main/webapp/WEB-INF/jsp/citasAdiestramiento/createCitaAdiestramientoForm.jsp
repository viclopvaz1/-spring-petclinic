<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citasAdiestramiento">
    <h2>
        <c:if test="${citaAdiestramiento['new']}">New </c:if> CitaAdiestramiento
    </h2>
    <form:form modelAttribute="citaAdiestramiento" class="form-horizontal" id="add-citaAdiestramiento-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Pet" name="pet.id"/>
            <petclinic:inputField label="Fecha inicio" name="cita.fechaInicio"/>
            <petclinic:inputField label="Adiestrador" name="adiestrador"/>
            <petclinic:inputField label="Tipo Adiestramiento" name="tipoAdiestramiento"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${citaAdiestramiento['new']}">
                        <button class="btn btn-default" type="submit">Add citaAdiestramiento</button>
                    </c:when>
                   
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
