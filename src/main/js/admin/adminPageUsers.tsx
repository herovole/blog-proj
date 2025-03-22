import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {TagService} from "../service/tags/tagService";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {AuthService} from "../service/auth/authService";
import {SearchAdminUserOutput} from "../service/auth/searchAdminUserOutput";
import {AdminUsers} from "./fragment/user/adminUsers";
import {SearchAdminUserInput} from "../service/auth/searchAdminUserInput";

export const AdminPageUsers: React.FC = () => {
    const tagService: TagService = new TagService();
    const authService: AuthService = new AuthService();
    const [roles, setRoles] = React.useState<TagUnits>(TagUnits.empty());
    const [users, setUsers] = React.useState<SearchAdminUserOutput>(SearchAdminUserOutput.empty());
    const [refresh, setRefresh] = React.useState(false);

    const load = async (): Promise<void> => {

        const rolesOutput: SearchTagsOutput = await tagService.searchRoles();
        if (rolesOutput.isSuccessful()) {
            setRoles(rolesOutput.getTagUnits());
        } else {
            console.error(rolesOutput.getMessage("roles"));
        }

        const usersInput: SearchAdminUserInput = new SearchAdminUserInput(1, 10000, true);
        const usersOutput: SearchAdminUserOutput = await authService.searchAdminUser(usersInput);
        if (usersOutput.isSuccessful()) {
            setUsers(usersOutput);
        } else {
            console.error(usersOutput.getMessage("roles"));
        }
    };
    useEffect(() => {
        load().then();
    }, [refresh]);

    const reload = () => {
        setRefresh(r => !r);
    }

    if (roles.isEmpty() || users.isEmpty()) {
        return (
            <AdminBasicLayout>
                <div>Loading...</div>
            </AdminBasicLayout>
        );
    } else {
        return (
            <AdminBasicLayout>
                <AdminUsers data={users} roles={roles} reload={reload}/>
            </AdminBasicLayout>
        );
    }
};
