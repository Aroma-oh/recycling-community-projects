import axios from "axios";

axios.defaults.baseURL = process.env.NEXT_PUBLIC_API_URL;

// 📌 Detail api

/*
export const GetSeries = async (seriesId: string) => {
  try {
    const response = await axios.get(`/series/${seriesId}`);
    axios.defaults.headers.common["Authorization"] = `Bearer ${response.headers.authorization}`;
    return response.data.data;
  } catch (error) {
    throw error;
  }
}
*/


export const GetDaylog = async (seriesId: string, page: number) => {
  try {
    const response = await axios.get(`/series/${seriesId}/daylog?page=${page}`, {
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWQiOjQsInN1YiI6ImhnZDEyMzQ1NUBuYXZlci5jb20iLCJpYXQiOjE2ODQzOTk0NTQsImV4cCI6MTY4NDQwMTI1NH0.B92sRYmdFjKoa5AnQlUx3o1o-5Wy3AX9gmfMiykZab0M6qyinfYF2n2hOIl-5HQk",
      },
    });
    // axios.defaults.headers.common["Authorization"] = `Bearer ${response.headers.authorization}`;
    return response.data.data;
  } catch (error) {
    throw error;
  }
}
