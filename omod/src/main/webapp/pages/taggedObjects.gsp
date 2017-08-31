<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("uicommons", "angular-resource.min.js")
    ui.includeJavascript("uicommons", "angular-app.js")
    ui.includeJavascript("uicommons", "angular-common.js")
    ui.includeJavascript("tag","taggedObjects.js")
    ui.includeJavascript("tag", "service/tagService.js")
    ui.includeJavascript("uicommons", "services/personService.js")
%>

<script type="text/javascript">

    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("tag.home.title") }"},
    ];
</script>

<div class="taggedObjects_display" ng-app="tag.taggedObjects" ng-controller="taggedObjectsCtrl" ng-init="init('${param.tag[0]}','${param.returnUrl[0]}')">
    <h2> <%= ui.message("tag.taggedListHeading", "{{ tag }}") %></h2>
    <table id="taggedPersons" width="100%" border="1" cellspacing="0" cellpadding="2" ng-show="dataLoaded">
        <thead>
        <tr>
            <th>${ ui.message("tag.patientName") }</th>
            <th>${ ui.message("tag.patientGender") }</th>
            <th>${ ui.message("tag.patientAge") }</th>
            <th>${ ui.message("tag.BirthDate") }</th>
        </tr>
        </thead>
   <tbody>
        <tr ng-repeat="patient in patients">
            <td>{{patient.display}}</td>
            <td>{{patient.gender}}</td>
            <td>{{patient.age}}</td>
            <td>{{patient.birthdate}}</td>
        </tr>
   </tbody>
    </table>
    <div><br/>
        <button ng-click="navigate()">
            ${ ui.message("uicommons.return") }
        </button>
    </div>
</div>
