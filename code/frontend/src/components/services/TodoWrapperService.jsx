import React, { useState, useEffect } from 'react';
import { axiosInstance } from '../../common/axios-instance';
import { AxiosResponse } from 'axios';

const TodoWrapperService = async () => {
  try {
    const response = await axiosInstance.get('api/task'); // substitua 'URL_DA_API' pela URL real da sua API
    console.log(response)
    return response;
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
};

export default TodoWrapperService;
