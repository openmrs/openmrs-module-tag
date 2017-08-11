<%
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("tag","app.js")
    ui.includeJavascript("uicommons", "angular-common-error.js")
    ui.includeJavascript("tag", "service/tagService.js")
    ui.includeJavascript("uicommons", "angular.js")
    ui.includeJavascript("uicommons", "angular-resource.min.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

%>

<div class="info-section tags" ng-app="Tag" ng-controller="tagCtrl" ng-init="init('${ patient.patient.uuid }')">
    <div class="info-header">
        <script type="text/ng-template" id="addDialogTemplate">
        <div class="dialog-header">
            <h3> ${ ui.message("tag.addTag")}</h3>
        </div>
        <div class="dialog-content">
            <div style="text-align:center;">
                <input type="text"  placeholder="Enter Tag" ng-model="addedTag" />
            </div>
            <div>
                <button class="confirm right" ng-click="confirm(addedTag)">${ ui.message("uicommons.save") }</button>
                <button class="cancel" ng-click="closeThisDialog()">${ ui.message("uicommons.cancel") }</button>
            </div>
        </div>
        </script>

        <i class="icon-tags"></i>
        <h3>${ ui.message("tag.HeaderMessage").toUpperCase() }</h3>
        <a ng-click="showAddDialog()"><i class="icon-plus add-action right"></i></a>
    </div>
        <div class="info-body">
                <script type="text/ng-template" id="dialogTemplate">
                <div class="dialog-header">
                    <h3>${ui.message("tag.deleteTag")}</h3>
                </div>
                <div class="dialog-content">
                    <div>
                        <label>
                            <%= ui.message("tag.deleteTagMessage", "{{ ngDialogData.tag }}") %>
                        </label>
                    </div>
                    <div>
                        <button class="confirm right" ng-click="confirm()">${ ui.message("uicommons.confirm") }</button>
                        <button class="cancel" ng-click="closeThisDialog()">${ ui.message("uicommons.cancel") }</button>
                    </div>
                </div>
                </script>
                <ul>
                    <li ng-repeat="tag in tags">
                        {{ tag.display }}<a ng-click="showDialog(tag)"><i class="icon-remove delete-action right" title=${ui.message("tag.deleteTitle")}></i></a>
                    </li>
                </ul>
                <p ng-show=" tags.length === 0 || tags == null">${ui.message("tag.Empty")}</p>
    </div>
    </div>