import { END_POINT } from "../axiosConstants";
import { axiosInstance } from "../axiosInstance";

export async function getRelations(search) {
    const response = await axiosInstance.get(
        END_POINT.RELATIONS + `?search=${search}&tag=`
    );

    return response.data.data;
}

export async function getRelation(oppositeId) {
    const response = await axiosInstance.get(
        END_POINT.RELATIONS_PATH(oppositeId)
    );
    return response.data.data;
}
