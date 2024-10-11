import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "../axiosInstance";

export async function getRelations(search) {
    const response = await axiosInstance.get(
        END_POINT.RELATIONS + `?search=${search}&tag=`
    );

    return response.data.data;
}
export async function updateRelations(data) {
    const response = await axiosInstance.patch(END_POINT.RELATION, data);
    return response.data.data;
}
