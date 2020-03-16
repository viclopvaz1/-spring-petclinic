<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donaciones">
    <h2>
        <c:if test="${donacion['new']}">New </c:if> Donacion
    </h2>
    <form:form modelAttribute="donacion" class="form-horizontal" id="add-donacion-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Donacion" name="cantidad"/>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${donacion['new']}">
                        <button class="btn btn-default" type="submit">Donar</button>
                    </c:when>
                </c:choose>
            </div>
            
        </div>
        <c:if test="${error}">
        	<h2>Te pasaste</h2>
        </c:if>
    </form:form>
</petclinic:layout>