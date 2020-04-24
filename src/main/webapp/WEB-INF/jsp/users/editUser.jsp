<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="persons">
    <h2>
        Monedero Actual
    </h2>
    <form:form modelAttribute="person" class="form-horizontal" id="add-person-form">
            <div id="divCheckbox" style="display: none;">
        		<petclinic:inputField label="LastName" name="lastName"/>
        		<petclinic:inputField label="firtName" name="firstName"/>
        	</div>
        <div class="form-group has-feedback">
            <petclinic:inputField label="Money To Add" name="monedero"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Add Money</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
