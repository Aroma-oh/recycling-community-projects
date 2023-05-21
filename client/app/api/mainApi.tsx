import axios from "axios";
axios.defaults.baseURL = process.env.NEXT_PUBLIC_API_URL;

// 📌 Main api

export const GetMain = async (pageQuery: number) => {
  try {
    const response = await axios.get(`/series?page=${pageQuery}`);
    return response.data.data.pagedata;
  } catch (error) {
    throw error;
  }
};
