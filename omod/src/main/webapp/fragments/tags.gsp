<%
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("tag","app.js")

%>

<div class="info-section tags">
    <div class="info-header">
        <i class="icon-medical"></i>
        <h3>${ ui.message("Tags").toUpperCase() }</h3>
    <div class="info-body">
        <div ng-app="Tag" ng-controller="tagCtrl" ng-init="init('${ patient.patient.uuid })">
            <ul >
            <li ng-repeat="tag in tags">
            {{tag.tag}}
                <i class="icon-remove delete-action" title="DELETE" onclick="Tag.deleteTag(tag.tag)"></i>
    </li>
        </ul>
    </div>
    </div>
    </div>
</div>